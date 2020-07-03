package fr.gouv.beta.fabnum.kelrisks.transverse.apiclient;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.ArrayList;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class GeorisquePaginatedCatNat extends BasicGeorisqueBean {
    
    List<CatNat> data = new ArrayList<>();
    
    @Data
    public static class CatNat {
        
        String code_national_catnat;  //    69PREF19820127
        String date_debut_evt;  //          06/11/1982
        String date_fin_evt;  //            10/11/1982
        String date_publication_arrete;  // 18/11/1982
        String date_publication_jo;  //     19/11/1982
        String libelle_risque_jo;  //       Tempête
        String code_insee;  //              69123
        String libelle_commune;  //         LYON
    }
}