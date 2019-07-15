package fr.gouv.beta.fabnum.kelrisks.presentation.controller;

import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedRadon;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedSismique;

import java.time.Duration;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

@RestController
public class GeorisqueController {
    
    private static final String GEORISQUE_BASE_URL = "http://www.georisques.gouv.fr/api/v1";
    private static final String RADON_URL          = GEORISQUE_BASE_URL + "/radon?code_insee=PARAM_INSEE&page=1&page_size=10";
    private static final String SISMIQUE_URL       = GEORISQUE_BASE_URL + "/zonage_sismique?&code_insee=PARAM_INSEE&page=1&page_size=10";
    
    @GetMapping("/georisques/radon/{insee}")
    public GeorisquePaginatedRadon radon(@PathVariable("insee") String query) {
        
        String uri = RADON_URL.replace("PARAM_INSEE", query);
        
        WebClient webClient = WebClient.create();
        
        return webClient.get()
                       .uri(uri)
                       .accept(MediaType.APPLICATION_JSON)
                       .exchange()
                       .flatMap(clientResponse -> clientResponse.bodyToMono(GeorisquePaginatedRadon.class))
                       .block(Duration.ofSeconds(10L));
    }
    
    @GetMapping("/georisques/sismique/{insee}")
    public GeorisquePaginatedSismique sismique(@PathVariable("insee") String query) {//String codeINSEE, double lat, double lon) {
        
        String uri = SISMIQUE_URL.replace("PARAM_INSEE", query);
        
        WebClient webClient = WebClient.create();
        
        return webClient.get()
                       .uri(uri)
                       .accept(MediaType.APPLICATION_JSON)
                       .exchange()
                       .flatMap(clientResponse -> clientResponse.bodyToMono(GeorisquePaginatedSismique.class))
                       .block(Duration.ofSeconds(10L));
    }
}
