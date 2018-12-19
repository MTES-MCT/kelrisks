package fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel;

import lombok.Data;

@Data
public class AdresseDTO {
    
    private String nomVoie;
    private String numero;
    private String codeINSEE;
    private String codePostal;
    private String nomCommune;
    private String idBAN;
    private String complement;
    private Long   id;
}