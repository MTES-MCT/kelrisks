package fr.gouv.beta.fabnum.kelrisks.metier.referentiel;

import fr.gouv.beta.fabnum.commun.metier.IWebClient;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.IIGNCartoService;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.IGNCartoAssiettePaginatedFeatures;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.IGNCartoGenerateurPaginatedFeatures;

import java.net.URI;
import java.time.Duration;

import javax.ws.rs.core.UriBuilder;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

@Service("iGNCartoService")
public class IGNCartoService implements IIGNCartoService, IWebClient {
    
    private static final String IGNCarto_BASE_URL = "https://apicarto.ign.fr/api";
    private static final String ASSIETTE_URL      = IGNCarto_BASE_URL + "/gpu/assiette-sup-s";
    private static final String SURFACIQUE_URL    = IGNCarto_BASE_URL + "/gpu/generateur-sup-s";
    
    @Override
    public IGNCartoAssiettePaginatedFeatures rechercherAssiettesContenantPolygon(String geom) {
    
        UriBuilder uriBuilder  = UriBuilder.fromPath(ASSIETTE_URL);
        URI        assietteUri = uriBuilder.queryParam("geom", "{geom}").build(geom);
    
        return getWebClient().get()
                       .uri(assietteUri)
                       .accept(MediaType.APPLICATION_JSON)
                       .retrieve()
                       .bodyToMono(IGNCartoAssiettePaginatedFeatures.class)
                       .block(Duration.ofSeconds(60L));
    }
    
    @Override
    public IGNCartoGenerateurPaginatedFeatures rechercherGenerateur(String partition) {
        
        UriBuilder uriBuilder = UriBuilder.fromPath(SURFACIQUE_URL);
        URI generateurUri = uriBuilder
                                    .queryParam("partition", "{partition}")
                                    .build(partition);
    
        return getWebClient().get()
                       .uri(generateurUri)
                       .accept(MediaType.APPLICATION_JSON)
                       .retrieve()
                       .bodyToMono(IGNCartoGenerateurPaginatedFeatures.class)
                       .block(Duration.ofSeconds(60L));
    }
}
