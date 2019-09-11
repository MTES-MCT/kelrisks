package fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel;

import lombok.Data;

@Data
public class SiteIndustrielBasiasDTO {
    
    private Long   id;
    private String adresseId;
    private String identifiant;
    private String adresse;
    private String raisonSociale;
    private String ewkt;
    private String precision;
}
  