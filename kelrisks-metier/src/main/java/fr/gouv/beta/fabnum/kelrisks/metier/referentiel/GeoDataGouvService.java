package fr.gouv.beta.fabnum.kelrisks.metier.referentiel;

import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.IGeoDataGouvService;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeoDataGouvCommune;
import reactor.core.publisher.Mono;

import java.time.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service("geoDataGouvService")
public class GeoDataGouvService implements IGeoDataGouvService {
    
    private static final String ADRESSE_BASE_URL = "https://geo.api.gouv.fr";
    private static final String COMMUNE_URL      = ADRESSE_BASE_URL + "/communes?lat=PARAM_LAT&lon=PARAM_LON&fields=code&format=json&geometry=centre";
    
    @Autowired
    WebClient webClient;
    
    public GeoDataGouvCommune rechercherCommune(String latitude, String longitude) {
        
        String uri = COMMUNE_URL.replace("PARAM_LAT", latitude).replace("PARAM_LON", longitude);
        
        WebClient webClient = WebClient.create();
        
        GeoDataGouvCommune[] block = webClient.get()
                                             .uri(uri)
                                             .accept(MediaType.APPLICATION_JSON)
                                             .retrieve()
                                             .bodyToMono(GeoDataGouvCommune[].class)
                                             .onErrorResume(e -> {
                                                 System.out.println(" V : " + e.getLocalizedMessage());
                                                 return Mono.just(new GeoDataGouvCommune[0]);
                                             })
                                             .block(Duration.ofSeconds(30L));
        
        if (block != null && block.length == 1) { return block[0]; }
        
        return null;
    }
}
