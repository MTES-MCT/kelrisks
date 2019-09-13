package fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel;

import lombok.Data;

@Data
public class SiteIndustrielBasiasDTO extends AbstractLocalisationAvecPrecision {
    
    private Long   id;
    private String adresseId;
    private String identifiant;
    private String adresse;
    private String raisonSociale;
    private String codeInsee;
}
  