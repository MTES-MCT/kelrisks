package fr.gouv.beta.fabnum.kelrisks;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

@Component
@ConfigurationProperties(prefix = "application")
@PropertySource(ResourceUtils.CLASSPATH_URL_PREFIX + "pdf.properties")
public class ApplicationProperties {
    // Configuration
}
