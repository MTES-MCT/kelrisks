package fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel;

import lombok.Data;

@Data
public class SecteurInformationSolDTO extends AbstractLocalisationAvecPrecision {
    
    private String id;
    private String nom;
    private String ficheRisque;
}
  