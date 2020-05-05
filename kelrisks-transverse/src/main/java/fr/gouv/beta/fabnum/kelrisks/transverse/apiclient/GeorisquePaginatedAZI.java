package fr.gouv.beta.fabnum.kelrisks.transverse.apiclient;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class GeorisquePaginatedAZI extends BasicGeorisqueBean {
    
    List<AZI> data = new ArrayList<>();
    
    @Data
    public static class AZI {
        
        String              code_national_azi;
        String              libelle_azi;
        List<LibelleRisque> liste_libelle_risque = new ArrayList<>();
        
        @Data
        public static class LibelleRisque {
            
            String num_risque;
            String libelle_risque_long;
        }
    }
}