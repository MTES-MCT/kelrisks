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
public class GeorisquePaginatedPPR extends BasicGeorisqueBean {
    
    List<PPR> data = new ArrayList<>();
    
    @Data
    public static class PPR {
        
        @JsonDeserialize(using = GeoJsonDeserialiser.class)
        @JsonSerialize(using = GeoJsonSerialiser.class)
        Geometry<?> geom_perimetre;
        String id_gaspar;
        String date_approbation;
    }
}
