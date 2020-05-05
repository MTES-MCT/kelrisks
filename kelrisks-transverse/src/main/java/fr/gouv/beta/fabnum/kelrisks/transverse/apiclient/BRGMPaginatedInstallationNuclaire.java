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
public class BRGMPaginatedInstallationNuclaire extends BasicGeorisqueBean {
    
    List<InstallationNucleaire> features = new ArrayList<>();
    
    @Data
    public static class InstallationNucleaire {
        
        @JsonDeserialize(using = GeoJsonDeserialiser.class)
        @JsonSerialize(using = GeoJsonSerialiser.class)
        private Geometry<?> geometry;
        
        private Properties properties = new Properties();
        
        @Data
        public static class Properties {
            
            private String gid;
            private String insee_com;
            private String nom_com;
            private String nom_inst;
            //            private String label;
            //            private String num_inb;
            //            private String info;
            //            private String type;
            //            private String site;
            //            private String exploitant;
            //            private String longitude;
            //            private String latitude;
            private String type_leg;
        }
    }
}