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
    
        String url = "/ign/assiette/{geoJSON}";
        String geoJSON = "{\"type\":\"Polygon\",\"coordinates\":[[[2.2018004,48.9168055],[2.2016485,48.9167433],[2.2015624,48.916708],[2.2013932,48.9166386],[2.2012002,48.9165598],[2.2011225,48" +
                         ".916528],[2.2009813,48.91647],[2.2007657,48.9163807],[2.2007283,48.9163646],[2.2005021,48.9162722],[2.2004168,48.9162372],[2.200313,48.9161947],[2.200239,48.916164],[2" +
                         ".2001007,48.916107],[2.1997879,48.9159778],[2.1996938,48.915939],[2.1995767,48.9158906],[2.1994628,48.9158438],[2.1993479,48.9157964],[2.1991278,48.9157074],[2.1993481,48" +
                         ".915465],[2.1995168,48.9152836],[2.1997737,48.9153923],[2.2000942,48.9155275],[2.2002344,48.9155869],[2.2003995,48.9156549],[2.2021626,48.9163965],[2.2021485,48.9164128]," +
                         "[2.2021098,48.9164583],[2.2018004,48.9168055]]]}";
    
        IGNCartoAssiettePaginatedFeatures assietteFeatures = restTemplate.getForObject(url,
                                                                                       IGNCartoAssiettePaginatedFeatures.class,
                                                                                       geoJSON);
    
        assertThat(assietteFeatures.getFeatures()).isNotEmpty();
        assertThat(assietteFeatures.getFeatures()).anyMatch(assiette -> assiette.getProperties().getPartition().equals("130012073_SUP_78_PM1"));
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