package fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel;

import lombok.Data;

@Data
public class AdresseDTO {
    
    //    private String     nomVoie;
    private String numero;
    private String idBAN;
    private String complement;
    private Long   id;
    private RueDTO rue;
}