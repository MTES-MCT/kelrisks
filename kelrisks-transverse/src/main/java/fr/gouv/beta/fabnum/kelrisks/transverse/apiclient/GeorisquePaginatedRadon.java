package fr.gouv.beta.fabnum.kelrisks.transverse.apiclient;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GeorisquePaginatedRadon extends BasicGeorisqueBean {
    
    List<Radon> data = new ArrayList<>();
    
    @Data
    public static class Radon {
        
        String classe_potentiel;
        String code_insee;
        String libelle_commune;
    }
}
