package fr.gouv.beta.fabnum.kelrisks.transverse.apiclient;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class AdresseDataGouvPaginatedFeatures extends BasicGeorisqueBean {
    
    List<Adresse> features = new ArrayList<>();
    
    @Data
    public static class Adresse {
        
        Properties properties = new Properties();
        
        @Data
        public static class Properties {
            
            String label;
            Double score;
            String id;
            String type;
            String name;
            String postcode;
            String citycode;
            Double x;
            Double y;
            String population;
            String city;
            String context;
            Double importance;
        }
    }
}
