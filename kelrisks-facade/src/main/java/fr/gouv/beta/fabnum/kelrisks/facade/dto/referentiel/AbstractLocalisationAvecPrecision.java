package fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel;

import lombok.Data;

@Data
public class AbstractLocalisationAvecPrecision {
    
    private String ewkt;
    private String precision;
}
