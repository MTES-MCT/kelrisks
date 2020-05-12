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
public class GeorisquePaginatedSIS extends BasicGeorisqueBean {
    
    List<SecteurInformationSols> data = new ArrayList<>();
    
    @Data
    public static class SecteurInformationSols {
        
        String nom;
        @JsonDeserialize(using = GeoJsonDeserialiser.class)
        @JsonSerialize(using = GeoJsonSerialiser.class)
        Geometry<?> geom;
        String id_sis;
        String fiche_risque;
    }
}