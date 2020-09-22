package fr.gouv.beta.fabnum.kelrisks.transverse.apiclient;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class GeorisquePaginatedTRI extends BasicGeorisqueBean {
    
    List<TRI> data = new ArrayList<>();
    
    @Data
    public static class TRI {
        
        String              code_national_tri;
        String              libelle_tri;
        List<LibelleRisque> liste_libelle_risque = new ArrayList<>();
        
        @Data
        public static class LibelleRisque {
            
            String num_risque;
            String libelle_risque_long;
        }
    }
}