package fr.gouv.beta.fabnum.kelrisks.transverse.apiclient;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class GeorisquePaginatedSismique extends BasicGeorisqueBean {
    
    List<ZonageSismique> data = new ArrayList<>();
    
    @Data
    public static class ZonageSismique {
        
        String code_zone;
        String zone_sismicite;
        String code_insee;
        String libelle_commune;
    }
}
