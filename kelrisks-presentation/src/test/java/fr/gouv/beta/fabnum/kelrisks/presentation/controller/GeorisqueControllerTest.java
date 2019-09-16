package fr.gouv.beta.fabnum.kelrisks.presentation.controller;

import static org.assertj.core.api.Assertions.assertThat;

import fr.gouv.beta.fabnum.kelrisks.Application;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedRadon;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedSismique;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class GeorisqueControllerTest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    public void saintEtienneDoitAvoirUneClasseRadonDe3() {
        
        GeorisquePaginatedRadon radon = restTemplate.getForObject("/georisques/radon/{insee}",
                                                                  GeorisquePaginatedRadon.class,
                                                                  "42218");
        
        assertThat(radon.getData().get(0).getClasse_potentiel()).isEqualToIgnoringCase("3");
    }
    
    @Test
    public void saintEtienneDoitAvoirUnCodeZoneSismiqueDe2() {
        
        GeorisquePaginatedSismique sismique = restTemplate.getForObject("/georisques/sismique/{insee}",
                                                                        GeorisquePaginatedSismique.class,
                                                                        "42218");
        
        assertThat(sismique.getData().get(0).getCode_zone()).isEqualToIgnoringCase("2");
    }
}