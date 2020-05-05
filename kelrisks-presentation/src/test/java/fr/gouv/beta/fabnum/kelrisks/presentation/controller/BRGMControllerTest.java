package fr.gouv.beta.fabnum.kelrisks.presentation.controller;

import static org.assertj.core.api.Assertions.assertThat;

import fr.gouv.beta.fabnum.kelrisks.Application;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.BRGMPaginatedCanalisation;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.BRGMPaginatedInstallationNuclaire;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BRGMControllerTest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    public void communeDoitRetournerDesCanalisations() {
        
        BRGMPaginatedCanalisation canalisation = restTemplate.getForObject("/brgm/canalisation/{insee}/",
                                                                           BRGMPaginatedCanalisation.class,
                                                                           "45284");
        
        assertThat(canalisation.getFeatures()).isNotEmpty();
    }
    
    @Test
    public void CommuneDoitRetournerDesInstallations() {
        
        BRGMPaginatedInstallationNuclaire nucleaire = restTemplate.getForObject("/brgm/nucleaire/{insee}",
                                                                                BRGMPaginatedInstallationNuclaire.class,
                                                                                "50600");
        
        assertThat(nucleaire.getFeatures()).isNotEmpty();
    }
    
    @Test
    public void coordonneesDoiventRetournerDesCanalisations() {
        
        BRGMPaginatedCanalisation canalisation = restTemplate.getForObject("/brgm/canalisation/{lon}/{lat}/{rayon}/",
                                                                           BRGMPaginatedCanalisation.class,
                                                                           "1.90587", "47.8998", "5000");
        
        assertThat(canalisation.getFeatures()).isNotEmpty();
    }
    
    @Test
    public void coordonneesDoiventRetournerDesInstallations() {
        
        BRGMPaginatedInstallationNuclaire nucleaire = restTemplate.getForObject("/brgm/nucleaire/{lon}/{lat}/{rayon}",
                                                                                BRGMPaginatedInstallationNuclaire.class,
                                                                                "-1.8365", "49.66469", "10000");
        
        assertThat(nucleaire.getFeatures()).isNotEmpty();
    }
}