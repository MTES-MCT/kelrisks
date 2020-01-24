package fr.gouv.beta.fabnum.kelrisks.transverse.apiclient;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class IGNCartoGenerateurPaginatedFeatures {
    
    List<Generateur> features = new ArrayList<>();
    
    @Data
    public static class Generateur {
        
        Properties properties = new Properties();
//        String     geometry;
        
        @Data
        public static class Properties {
            
            String idgen;
            String idsup;
            String partition;
            String fichier;
            String nomgen;
            String typegen;
            String id_gaspar;
            String code_alea;
            String url_grisq;
        }
    }
}
