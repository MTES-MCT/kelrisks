package fr.gouv.beta.fabnum.kelrisks.transverse.apiclient;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import org.geolatte.geom.Geometry;

@Data
public class IGNCartoAssiettePaginatedFeatures {
    
    List<Assiette> features = new ArrayList<>();
    
    @Data
    public static class Assiette {
        
        Properties  properties = new Properties();
//        Geometry<?> geometry;
        
        @Data
        public static class Properties {
            
            String idass;
            String partition;
            String fichier;
            String idgen;
        }
    }
}
