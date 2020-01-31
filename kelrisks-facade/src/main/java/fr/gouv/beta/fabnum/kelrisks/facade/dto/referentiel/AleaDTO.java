package fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel;

import lombok.Data;

@Data
public class AleaDTO {
    
    private String         codeGaspar;
    private String         code;
    private String         libelle;
    private FamilleAleaDTO familleAlea;
}
