package fr.gouv.beta.fabnum.kelrisks.metier.referentiel;

import fr.gouv.beta.fabnum.commun.metier.IWebClient;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.IAdresseDataGouvService;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.AdresseDataGouvPaginatedFeatures;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

@Service("adresseDataGouvService")
public class AdresseDataGouvService implements IAdresseDataGouvService, IWebClient {
    
    private static final String ADRESSE_BASE_URL = "https://api-adresse.data.gouv.fr/search";
    private static final String COMMUNE_URL      = ADRESSE_BASE_URL + "/?q=PARAM_INSEE&limit=1";
    
    public List<AdresseDataGouvPaginatedFeatures.Adresse> rechercherCommune(String codeINSEE) {
        
        String uri = COMMUNE_URL.replace("PARAM_INSEE", codeINSEE);
        
        WebClient webClient = WebClient.create();
        
        AdresseDataGouvPaginatedFeatures block = getWebClient().get()
                                                         .uri(uri)
                                                         .accept(MediaType.APPLICATION_JSON)
                                                         .exchange()
                                                         .flatMap(clientResponse -> clientResponse.bodyToMono(AdresseDataGouvPaginatedFeatures.class))
                                                         .block(Duration.ofSeconds(30L));
        
        if (block != null) { return block.getFeatures(); }
        
        return new ArrayList<>();
    }
}
