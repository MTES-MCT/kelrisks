package fr.gouv.beta.fabnum.kelrisks.transverse.apiclient;

import fr.gouv.beta.fabnum.commun.utils.GeoJsonDeserialiser;
import fr.gouv.beta.fabnum.commun.utils.GeoJsonSerialiser;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

import org.geolatte.geom.Geometry;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

@Data
@EqualsAndHashCode(callSuper = true)
public class BRGMPaginatedCanalisation extends BasicGeorisqueBean {
    
    List<Canalisation> features = new ArrayList<>();
    
    @Data
    public static class Canalisation {
        
        @JsonDeserialize(using = GeoJsonDeserialiser.class)
        @JsonSerialize(using = GeoJsonSerialiser.class)
        private Geometry<?> geometry;
        
        private Properties properties = new Properties();
        
        @Data
        public static class Properties {
    
            //            private String gid;
            private String num_com;
            private String nom_com;
            //            private String transporteur;
            //            private String desc_ouv;
            //            private String cat_fluide;
            //            private String longueur;
        }
    }
}