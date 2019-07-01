package fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel;

import lombok.Data;

@Data
public class SiteIndustrielBasiasDTO {
    
    private Long   id;
    private String identifiant;
    private String adresse;
    private String codeActivite;
    private String raisonSociale;
    private String geolocalisation;
    private String ewkt;
    private String precision;
}
  