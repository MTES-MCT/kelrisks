package fr.gouv.beta.fabnum.kelrisks.transverse.apiclient;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class GeorisquePaginatedRadon extends BasicGeorisqueBean {
    
    List<Radon> data = new ArrayList<>();
    
    @Data
    public static class Radon {
        
        String classe_potentiel;
        String code_insee;
        String libelle_commune;
    }
}