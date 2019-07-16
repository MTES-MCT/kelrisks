package fr.gouv.beta.fabnum.kelrisks.presentation.controller;

import static org.assertj.core.api.Assertions.assertThat;

import fr.gouv.beta.fabnum.kelrisks.Application;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ResourcesControllerTest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    public void doitRetournerUneImageDeTaille76667() {
        
        byte[] image = restTemplate.getForObject("/api/image/{image}",
                                                 byte[].class,
                                                 "marianne");
        
        assertThat(image).hasSize(76667);
    }
}