package fr.gouv.beta.fabnum.kelrisks.metier.referentiel;

import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.IBRGMService;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.BRGMPaginatedCanalisation;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.BRGMPaginatedInstallationNuclaire;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.time.Duration;

import javax.ws.rs.core.UriBuilder;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service("brgmService")
public class BRGMService implements IBRGMService {
    
    private static final String BRGM_BASE_URL                            = "https://mapsref.brgm.fr/wxs/georisques/rapport?";
    private static final String CANALISATIONS_COORDONNEES_URL            = BRGM_BASE_URL + "version=1.0.0&service=wfs&request=getfeature&typename=CANALISATIONS&propertyname=gid,num_com,nom_com," +
                                                                           "transporteur,desc_ouv,cat_fluide,longueur&outputformat=geojson";
    private static final String CANALISATIONS_COMMUNE_URL                = BRGM_BASE_URL + "version=1.0.0&service=wfs&request=getfeature&typename=CANALISATIONS_COMMUNE&propertyname=gid,num_com," +
                                                                           "nom_com,transporteur,desc_ouv,cat_fluide,longueur&numinsee=PARAM_INSEE&outputformat=geojson";
    private static final String INSTALLATIONS_NUCLEAIRES_COORDONNEES_URL = BRGM_BASE_URL + "&service=wfs&version=2.0.0&request=getfeature&typename=INSTALLATIONS_NUCLEAIRES&outputformat=geojson";
    private static final String INSTALLATIONS_NUCLEAIRES_COMMUNE_URL     = BRGM_BASE_URL + "&service=wfs&version=2.0.0&request=getfeature&typename=INSTALLATIONS_NUCLEAIRES_COMMUNE&numinsee" +
                                                                           "=PARAM_INSEE&rayon=10000&outputformat=geojson";
    
    @Autowired
    WebClient webClient;
    
    public BRGMPaginatedCanalisation rechercherCanalisationsCommune(String codeINSEE) {
        
        String uri = CANALISATIONS_COMMUNE_URL.replace("PARAM_INSEE", codeINSEE);
        
        return webClient.get()
                       .uri(uri)
                       .accept(MediaType.APPLICATION_JSON)
                       .retrieve()
                       .bodyToMono(BRGMPaginatedCanalisation.class)
                       .onErrorResume(e -> {
                           System.out.println(" V : " + e.getLocalizedMessage());
                           return Mono.just(new BRGMPaginatedCanalisation());
                       })
                       .block(Duration.ofSeconds(30L));
    }
    
    @Override
    public BRGMPaginatedCanalisation rechercherCanalisationsCoordonnees(String lon, String lat, int rayon) {
    
        UriBuilder uriBuilder = UriBuilder.fromPath(CANALISATIONS_COORDONNEES_URL);
        URI generateurUri = uriBuilder
                                    .queryParam("rayon", String.valueOf(rayon))
                                    .queryParam("X", lon)
                                    .queryParam("Y", lat)
                                    .build();
    
        return webClient.get()
                       .uri(generateurUri)
                       .accept(MediaType.APPLICATION_JSON)
                       .retrieve()
                       .bodyToMono(BRGMPaginatedCanalisation.class)
                       .onErrorResume(e -> {
                           System.out.println(" V : " + e.getLocalizedMessage());
                           return Mono.just(new BRGMPaginatedCanalisation());
                       })
                       .block(Duration.ofSeconds(30L));
    }
    
    public BRGMPaginatedInstallationNuclaire rechercherInstallationsNucleairesCommune(String codeINSEE) {
    
        String uri = INSTALLATIONS_NUCLEAIRES_COMMUNE_URL.replace("PARAM_INSEE", codeINSEE);
    
        return webClient.get()
                       .uri(uri)
                       .accept(MediaType.APPLICATION_JSON)
                       .retrieve()
                       .bodyToMono(BRGMPaginatedInstallationNuclaire.class)
                       .onErrorResume(e -> {
                           System.out.println(" V : " + e.getLocalizedMessage());
                           return Mono.just(new BRGMPaginatedInstallationNuclaire());
                       })
                       .block(Duration.ofSeconds(30L));
    }
    
    @Override
    public BRGMPaginatedInstallationNuclaire rechercherInstallationsNucleairesCoordonnees(String lon, String lat, int rayon) {
    
        UriBuilder uriBuilder = UriBuilder.fromPath(INSTALLATIONS_NUCLEAIRES_COORDONNEES_URL);
        URI generateurUri = uriBuilder
                                    .queryParam("rayon", String.valueOf(rayon))
                                    .queryParam("X", lon)
                                    .queryParam("Y", lat)
                                    .build();
    
        return webClient.get()
                       .uri(generateurUri)
                       .accept(MediaType.APPLICATION_JSON)
                       .retrieve()
                       .bodyToMono(BRGMPaginatedInstallationNuclaire.class)
                       .onErrorResume(e -> {
                           System.out.println(" V : " + e.getLocalizedMessage());
                           return Mono.just(new BRGMPaginatedInstallationNuclaire());
                       })
                       .block(Duration.ofSeconds(30L));
    }
}
