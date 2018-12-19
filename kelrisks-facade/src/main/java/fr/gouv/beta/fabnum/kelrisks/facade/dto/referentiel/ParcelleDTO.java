package fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel;

import lombok.Data;

import org.geolatte.geom.Geometry;

@Data
public class ParcelleDTO {
    
    private Long     id;
    private String   type;
    private String   code;
    private String   type_geom;
    private String   commune;
    private Geometry multiPolygon;
    private String   prefixe;
    private String   section;
    private String   numero;
}
  