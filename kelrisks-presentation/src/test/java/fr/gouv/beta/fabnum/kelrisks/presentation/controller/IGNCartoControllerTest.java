package fr.gouv.beta.fabnum.kelrisks.presentation.controller;

import static org.assertj.core.api.Assertions.assertThat;

import fr.gouv.beta.fabnum.kelrisks.Application;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.IGNCartoAssiettePaginatedFeatures;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.IGNCartoGenerateurPaginatedFeatures;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IGNCartoControllerTest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    public void laGeometrieDoitRetournerUnePartition() {
        
        String url     = "/ign/assiette/{geoJSON}";
        String geoJSON = "{\"type\":\"Polygon\",\"coordinates\":[[[2.3138931,48.9582821],[2.4219254,48.8492427],[2.5726089,48.8202455],[2.5524193,49.0017666],[2.3138931,48.9582821]]]}";
        
        IGNCartoAssiettePaginatedFeatures assietteFeatures = restTemplate.getForObject(url,
                                                                                       IGNCartoAssiettePaginatedFeatures.class,
                                                                                       geoJSON);
        
        assertThat(assietteFeatures.getFeatures()).isNotEmpty();
        assertThat(assietteFeatures.getFeatures()).anyMatch(assiette -> assiette.getProperties().getPartition().equals("130002926_SUP_95_PM1"));
    }
    
    @Test
    public void laGeometrieDoitRetournerUnIdGaspar() {
        
        String url       = "/ign/generateur/{geoJSON}/{partition}";
        String geoJSON   = "{\"type\":\"Polygon\",\"coordinates\":[[[2.3138931,48.9582821],[2.4219254,48.8492427],[2.5726089,48.8202455],[2.5524193,49.0017666],[2.3138931,48.9582821]]]}";
        String partition = "130002926_SUP_95_PM1";
        
        IGNCartoGenerateurPaginatedFeatures generateurFeatures = restTemplate.getForObject(url,
                                                                                           IGNCartoGenerateurPaginatedFeatures.class,
                                                                                           geoJSON, partition);
    
        assertThat(generateurFeatures.getFeatures()).isNotEmpty();
        assertThat(generateurFeatures.getFeatures()).anyMatch(generateur -> generateur.getProperties().getId_gaspar().equals("95PREF20010179"));
    }
}