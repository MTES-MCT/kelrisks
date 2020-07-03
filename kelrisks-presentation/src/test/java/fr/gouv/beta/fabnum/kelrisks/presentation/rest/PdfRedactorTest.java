package fr.gouv.beta.fabnum.kelrisks.presentation.rest;

import static org.assertj.core.api.Assertions.assertThat;

import fr.gouv.beta.fabnum.kelrisks.Application;
import fr.gouv.beta.fabnum.kelrisks.facade.avis.AvisDTO;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import org.jsoup.Jsoup;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ResourceUtils;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Application.class}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PdfRedactorTest {
    
    @Autowired
    PdfRedactor pdfRedactor;
    
    @Test
    public void redigerAnalyse() throws IOException {
        
        AvisDTO avisDTO = new AvisDTO();
        
        File baseAvis = ResourceUtils.getFile(ResourceUtils.CLASSPATH_URL_PREFIX + "avis.html");
        
        org.jsoup.nodes.Document htmlDocument = Jsoup.parse(baseAvis, StandardCharsets.UTF_8.name());
        
        pdfRedactor.redigerAnalyse(htmlDocument, avisDTO, "91101");
        
        String html = htmlDocument.outerHtml();
        
        assertThat(html).contains("91101");
    }
}