package fr.gouv.beta.fabnum.kelrisks.transverse.apiclient;

import fr.gouv.beta.fabnum.commun.utils.GeoJsonDeserialiser;
import fr.gouv.beta.fabnum.commun.utils.GeoJsonSerialiser;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

import org.geolatte.geom.Geometry;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Data
public class IGNCartoGenerateurPaginatedFeatures {
    
    List<Generateur> features = new ArrayList<>();
    
    @Data
    public static class Generateur {
        
        Properties properties = new Properties();
        @JsonDeserialize(using = GeoJsonDeserialiser.class)
        @JsonSerialize(using = GeoJsonSerialiser.class)
        Geometry<?> geometry;
        
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
