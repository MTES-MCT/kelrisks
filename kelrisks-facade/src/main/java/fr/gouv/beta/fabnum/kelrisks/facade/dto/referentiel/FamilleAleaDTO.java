package fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel;

import lombok.Data;

@Data
public class FamilleAleaDTO {
    
    private String        code;
    private String        libelle;
    private FamillePprDTO famillePPR;
}
