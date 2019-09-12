package fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel;

import lombok.Data;

@Data
public class SiteIndustrielBasolDTO extends AbstractLocalisationAvecPrecision {
    
    private String adresseId;
    private Long   id;
    private String numerobasol;
    private String identifiantbasias;
    private String adresse;
    private String commune;
    private String codeinsee;
    private String proprietaire;
}
  