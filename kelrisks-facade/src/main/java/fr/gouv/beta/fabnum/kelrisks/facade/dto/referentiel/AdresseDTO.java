package fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel;

import lombok.Data;

@Data
public class AdresseDTO {
    
    private Long   id;
    private String nomVoie;
    private String numero;
    private String code_insee;
    private String code_post;
    private String nom_ld;
    private String nom_afnor;
    private String libelle_acheminement;
    private String nomCommune;
    private String idBAN;
}
  