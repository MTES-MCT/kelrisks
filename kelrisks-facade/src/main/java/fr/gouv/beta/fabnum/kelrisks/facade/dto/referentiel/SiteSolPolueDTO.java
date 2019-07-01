package fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel;

import lombok.Data;

import org.geolatte.geom.Geometry;

@Data
public class SiteSolPolueDTO {
    
    private Long     id;
    private String   nom;
    private String   description;
    private Geometry multiPolygon;
    private String   ewkt;
}
  