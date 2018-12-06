package fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel;

import lombok.Data;

@Data
public class ParcelleDTO {
    
    private Long   id;
    private String type;
    private String code;
    private String type_geom;
    private String commune;
}
  