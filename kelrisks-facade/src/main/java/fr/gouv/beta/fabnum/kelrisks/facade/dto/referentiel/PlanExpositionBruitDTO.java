package fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel;

import lombok.Data;

import java.util.Date;

import org.geolatte.geom.Geometry;

@Data
public class PlanExpositionBruitDTO {
    
    private String      zone;
    private Date        dateArret;
    private Geometry<?> multiPolygon;
}
