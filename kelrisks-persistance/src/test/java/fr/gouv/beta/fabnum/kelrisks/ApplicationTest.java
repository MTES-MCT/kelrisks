package fr.gouv.beta.fabnum.kelrisks;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication(scanBasePackages = {"fr.gouv.beta.fabnum"})
@EnableConfigurationProperties()
public class ApplicationTest {
}
