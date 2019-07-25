package fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel;

import lombok.Data;

import java.util.Date;

@Data
public class PlanPreventionRisquesDTO {
    
    private Long            id;
    private String          idGaspar;
    private Date            dateValidite;
    private String          libelle;
    private String          titre;
    private CategoriePprDTO categorie;
    
    @Data
    public static class CategoriePprDTO {
        
        private Long          id;
        private String        code;
        private String        libelle;
        private String        description;
        private FamillePprDTO famille;
        
        @Data
        public static class FamillePprDTO {
            
            private Long   id;
            private String code;
            private String libelle;
        }
    }
}
