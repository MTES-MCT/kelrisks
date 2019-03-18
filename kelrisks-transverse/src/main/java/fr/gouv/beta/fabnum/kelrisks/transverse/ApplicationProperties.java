package fr.gouv.beta.fabnum.kelrisks.transverse;

import lombok.Data;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "application")
@Data
public class ApplicationProperties {
    
    private Critere critere;
    
    @Data
    public static class Critere {
        
        private Tri tri;
        
        @Data
        public static class Tri {
            
            private String defaut;
        }
    }
}
