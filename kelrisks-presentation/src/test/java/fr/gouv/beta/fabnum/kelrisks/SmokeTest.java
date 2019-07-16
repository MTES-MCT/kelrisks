package fr.gouv.beta.fabnum.kelrisks;

import static org.assertj.core.api.Assertions.assertThat;

import fr.gouv.beta.fabnum.kelrisks.presentation.controller.AccueilController;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SmokeTest {
    
    @Autowired
    private AccueilController controller;
    
    @Test
    public void accueilControllerDoitExister() throws Exception {
        
        assertThat(controller).isNotNull();
    }
}
