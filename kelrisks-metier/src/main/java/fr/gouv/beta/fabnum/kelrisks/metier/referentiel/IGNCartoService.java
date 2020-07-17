package fr.gouv.beta.fabnum.kelrisks.metier.referentiel;

import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.IIGNCartoService;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.IGNCartoAssiettePaginatedFeatures;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.IGNCartoGenerateurPaginatedFeatures;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Duration;

import javax.ws.rs.core.UriBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service("iGNCartoService")
public class IGNCartoService implements IIGNCartoService {
    
    private static final String IGNCarto_BASE_URL = "https://apicarto-preprod.ign.fr/api";
    private static final String ASSIETTE_URL      = IGNCarto_BASE_URL + "/gpu/assiette-sup-s";
    private static final String SURFACIQUE_URL    = IGNCarto_BASE_URL + "/gpu/generateur-sup-s";
    
    @Autowired
    WebClient webClient;
    
    @Override
    public IGNCartoAssiettePaginatedFeatures rechercherAssiettesContenantPolygon(String geom) {
        
        UriBuilder uriBuilder  = UriBuilder.fromPath(ASSIETTE_URL);
        URI        assietteUri = uriBuilder.queryParam("geom", "{geom}").build(geom);
        
        IGNCartoAssiettePaginatedFeatures assietteFeatures = webClient.get()
                                                                     .uri(assietteUri)
                                                                     .accept(MediaType.APPLICATION_JSON)
                                                                     .retrieve()
                                                                     .bodyToMono(IGNCartoAssiettePaginatedFeatures.class)
                                                                     .onErrorResume(e -> {
                                                                         System.out.println(" V : " + e.getLocalizedMessage());
                                                                         return Mono.just(new IGNCartoAssiettePaginatedFeatures());
                                                                     })
                                                                     .block(Duration.ofSeconds(10L));
        return assietteFeatures;
    }
    
    @Override
    public IGNCartoGenerateurPaginatedFeatures rechercherGenerateurContenantPolygon(String geom, String partition) {
        
        UriBuilder uriBuilder = UriBuilder.fromPath(SURFACIQUE_URL);
        URI generateurUri = uriBuilder
                                    .queryParam("geom", "{geom}")
                                    .queryParam("partition", "{partition}")
                                    .build(geom, partition);
        
        return webClient.get()
                       .uri(generateurUri)
                       .accept(MediaType.APPLICATION_JSON)
                       .retrieve()
                       .bodyToMono(IGNCartoGenerateurPaginatedFeatures.class)
                       .onErrorResume(e -> {
                           System.out.println(" V : " + e.getLocalizedMessage());
                           return Mono.just(new IGNCartoGenerateurPaginatedFeatures());
                       })
                       .block(Duration.ofSeconds(10L));
    }
}
