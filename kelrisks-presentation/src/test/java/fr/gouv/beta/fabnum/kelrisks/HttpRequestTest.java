package fr.gouv.beta.fabnum.kelrisks;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class HttpRequestTest {
    
    @Autowired
    private TestRestTemplate restTemplate;
    
    @Test
    public void sanityTestDoitRetournerHelloWorld() throws Exception {
        
        assertThat(this.restTemplate.getForObject("/sanity_test",
                                                  String.class)).contains("Hello World");
    }
}