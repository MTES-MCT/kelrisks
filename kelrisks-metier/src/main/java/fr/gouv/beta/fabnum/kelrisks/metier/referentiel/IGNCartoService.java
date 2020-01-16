package fr.gouv.beta.fabnum.kelrisks.metier.referentiel;

import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.IIGNCartoService;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.IGNCartoAssiettePaginatedFeatures;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.IGNCartoGenerateurPaginatedFeatures;

import java.net.URI;
import java.time.Duration;

import javax.ws.rs.core.UriBuilder;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service("iGNCartoService")
public class IGNCartoService implements IIGNCartoService {
    
    private static final String IGNCarto_BASE_URL = "https://apicarto-preprod.ign.fr/api";
    private static final String ASSIETTE_URL      = IGNCarto_BASE_URL + "/gpu/assiette-sup-s";
    private static final String SURFACIQUE_URL    = IGNCarto_BASE_URL + "/gpu/generateur-sup-s";
    
    @Override
    public IGNCartoAssiettePaginatedFeatures rechercherAssiettesContenantPolygon(String geom) {
        
        WebClient webClient = WebClient.create();
        
        UriBuilder uriBuilder  = UriBuilder.fromPath(ASSIETTE_URL);
        URI        assietteUri = uriBuilder.queryParam("geom", "{geom}").build(geom);
    
        IGNCartoAssiettePaginatedFeatures assietteFeatures = webClient.get()
                                                          .uri(assietteUri)
                                                          .accept(MediaType.APPLICATION_JSON)
                                                          .exchange()
                                                          .flatMap(clientResponse -> clientResponse.bodyToMono(IGNCartoAssiettePaginatedFeatures.class))
                                                          .block(Duration.ofSeconds(10L));
        return assietteFeatures;
    }
    
    @Override
    public IGNCartoGenerateurPaginatedFeatures rechercherGenerateurContenantPolygon(String geom, String partition) {
        
        WebClient webClient = WebClient.create();
        
        UriBuilder uriBuilder = UriBuilder.fromPath(SURFACIQUE_URL);
        URI generateurUri = uriBuilder
                                    .queryParam("geom", "{geom}")
                                    .queryParam("partition", "{partition}")
                                    .build(geom, partition);
        
        return webClient.get()
                       .uri(generateurUri)
                       .accept(MediaType.APPLICATION_JSON)
                       .exchange()
                       .flatMap(clientResponse -> clientResponse.bodyToMono(IGNCartoGenerateurPaginatedFeatures.class))
                       .block(Duration.ofSeconds(10L));
    }
}
