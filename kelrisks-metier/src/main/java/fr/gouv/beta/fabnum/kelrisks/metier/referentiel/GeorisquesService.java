package fr.gouv.beta.fabnum.kelrisks.metier.referentiel;

import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.IGeorisquesService;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedAZI;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedCatNat;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedPPR;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedRadon;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedSIS;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedSismique;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedTRI;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Duration;

import javax.ws.rs.core.UriBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service("georisquesService")
public class GeorisquesService implements IGeorisquesService {
    
    private static final String GEORISQUE_BASE_URL = "https://www.georisques.gouv.fr/api/v1";
    private static final String RADON_URL          = GEORISQUE_BASE_URL + "/radon?code_insee=PARAM_INSEE&page=1&page_size=20";
    private static final String TRI_URL            = GEORISQUE_BASE_URL + "/gaspar/tri?rayon=1000&code_insee=PARAM_INSEE&page=1&page_size=20";
    private static final String AZI_URL            = GEORISQUE_BASE_URL + "/gaspar/azi?rayon=1000&code_insee=PARAM_INSEE&page=1&page_size=20";
    private static final String SISMIQUE_URL       = GEORISQUE_BASE_URL + "/zonage_sismique?&code_insee=PARAM_INSEE&page=1&page_size=20";
    private static final String CATNAT_URL         = GEORISQUE_BASE_URL + "/gaspar/catnat?rayon=1000&code_insee=PARAM_INSEE&page=1&page_size=100";
    private static final String SIS_URL            = GEORISQUE_BASE_URL + "/sis";
    private static final String PPR_URL            = GEORISQUE_BASE_URL + "/ppr";
    
    @Autowired
    WebClient webClient;
    
    public GeorisquePaginatedRadon rechercherRadonCommune(String codeINSEE) {
        
        String uri = RADON_URL.replace("PARAM_INSEE", codeINSEE);
        return webClient.get()
                       .uri(uri)
                       .accept(MediaType.APPLICATION_JSON)
                       .retrieve()
                       .bodyToMono(GeorisquePaginatedRadon.class)
                       .onErrorResume(e -> {
                           System.out.println(" V : " + e.getLocalizedMessage());
                           return Mono.just(new GeorisquePaginatedRadon());
                       })
                       .block(Duration.ofSeconds(30L));
    }
    
    public GeorisquePaginatedAZI rechercherAZICommune(String codeINSEE) {
    
        String uri = AZI_URL.replace("PARAM_INSEE", codeINSEE);
    
        return webClient.get()
                       .uri(uri)
                       .accept(MediaType.APPLICATION_JSON)
                       .retrieve()
                       .bodyToMono(GeorisquePaginatedAZI.class)
                       .onErrorResume(e -> {
                           System.out.println(" V : " + e.getLocalizedMessage());
                           return Mono.just(new GeorisquePaginatedAZI());
                       })
                       .block(Duration.ofSeconds(30L));
    }
    
    public GeorisquePaginatedTRI rechercherTRICommune(String codeINSEE) {
    
        String uri = TRI_URL.replace("PARAM_INSEE", codeINSEE);
    
        return webClient.get()
                       .uri(uri)
                       .accept(MediaType.APPLICATION_JSON)
                       .retrieve()
                       .bodyToMono(GeorisquePaginatedTRI.class)
                       .onErrorResume(e -> {
                           System.out.println(" V : " + e.getLocalizedMessage());
                           return Mono.just(new GeorisquePaginatedTRI());
                       })
                       .block(Duration.ofSeconds(30L));
    }
    
    public GeorisquePaginatedCatNat rechercherCatNatCommune(String codeINSEE) {
        
        String uri = CATNAT_URL.replace("PARAM_INSEE", codeINSEE);
        
        return webClient.get()
                       .uri(uri)
                       .accept(MediaType.APPLICATION_JSON)
                       .retrieve()
                       .bodyToMono(GeorisquePaginatedCatNat.class)
                       .onErrorResume(e -> {
                           System.out.println(" V : " + e.getLocalizedMessage());
                           return Mono.just(new GeorisquePaginatedCatNat());
                       })
                       .block(Duration.ofSeconds(30L));
    }
    
    public GeorisquePaginatedSismique rechercherSismiciteCommune(String codeINSEE) {
        
        String uri = SISMIQUE_URL.replace("PARAM_INSEE", codeINSEE);
        
        return webClient.get()
                       .uri(uri)
                       .accept(MediaType.APPLICATION_JSON)
                       .retrieve()
                       .bodyToMono(GeorisquePaginatedSismique.class)
                       .onErrorResume(e -> {
                           System.out.println(" V : " + e.getLocalizedMessage());
                           return Mono.just(new GeorisquePaginatedSismique());
                       })
                       .block(Duration.ofSeconds(30L));
    }
    
    @Override
    public GeorisquePaginatedSIS rechercherSisCoordonnees(String lon, String lat, int rayon) {
    
        String latlon = lon + "," + lat;
    
        UriBuilder uriBuilder = UriBuilder.fromPath(SIS_URL);
        URI generateurUri = uriBuilder
                                    .queryParam("rayon", String.valueOf(rayon))
                                    .queryParam("latlon", "{latlon}")
                                    .queryParam("page", "1")
                                    .queryParam("page_size", "10")
                                    .build(latlon);
    
        return webClient.get()
                       .uri(generateurUri)
                       .accept(MediaType.APPLICATION_JSON)
                       .retrieve()
                       .bodyToMono(GeorisquePaginatedSIS.class)
                       .onErrorResume(e -> {
                           System.out.println(" V : " + e.getLocalizedMessage());
                           return Mono.just(new GeorisquePaginatedSIS());
                       })
                       .block(Duration.ofSeconds(30L));
    }
    
    @Override
    public GeorisquePaginatedPPR rechercherPprCoordonnees(String lon, String lat, int rayon) {
    
        String latlon = lon + "," + lat;
    
        UriBuilder uriBuilder = UriBuilder.fromPath(PPR_URL);
        URI generateurUri = uriBuilder
                                    .queryParam("rayon", String.valueOf(rayon))
                                    .queryParam("latlon", "{latlon}")
                                    .queryParam("page", "1")
                                    .queryParam("page_size", "10")
                                    .build(latlon);
    
        return webClient.get()
                       .uri(generateurUri)
                       .accept(MediaType.APPLICATION_JSON)
                       .retrieve()
                       .bodyToMono(GeorisquePaginatedPPR.class)
                       .onErrorResume(e -> {
                           System.out.println(" V : " + e.getLocalizedMessage());
                           return Mono.just(new GeorisquePaginatedPPR());
                       })
                       .block(Duration.ofSeconds(30L));
    }
}
