package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel;

import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.PlanExpositionBruitDTO;

import java.util.List;

import org.geolatte.geom.Point;

public interface IGestionPlanExpositionBruitFacade {
    
    String rechercherZoneCentroid(Point<?> centroid);
    
    List<PlanExpositionBruitDTO> rechercherPlanExpositionBruitDansRayon(Point<?> centroid, double distance);
}