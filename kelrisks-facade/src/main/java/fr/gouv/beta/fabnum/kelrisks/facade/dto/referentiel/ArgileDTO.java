package fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel;

import lombok.Data;

import org.geolatte.geom.Geometry;

@Data
public class ArgileDTO {
    
    private Geometry<?> multiPolygon;
    private String      numeroDepartement;
    private int         niveauAlea;
}
