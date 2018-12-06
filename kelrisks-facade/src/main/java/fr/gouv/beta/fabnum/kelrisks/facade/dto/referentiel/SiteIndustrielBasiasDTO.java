package fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel;

import lombok.Data;

@Data
public class SiteIndustrielBasiasDTO {
    
    private Long   id;
    private String identifiant;
    private String adresse;
    private String code_activite;
    private String geolocalisation;
    private String precision;
}
  