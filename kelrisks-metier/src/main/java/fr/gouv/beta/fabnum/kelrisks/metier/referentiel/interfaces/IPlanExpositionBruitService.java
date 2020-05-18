package fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces;

import fr.gouv.beta.fabnum.commun.metier.IAbstractCRUDService;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.PlanExpositionBruit;

import java.util.List;

import org.geolatte.geom.Point;

/**
 * Interface du Service qui gère les entités PlanExpositionBruit
 */
public interface IPlanExpositionBruitService extends IAbstractCRUDService<PlanExpositionBruit> {
    
    String rechercherZoneCentroid(Point<?> centroid);
    
    List<PlanExpositionBruit> rechercherPlanExpositionBruitDansRayon(Point<?> centroid, double distance);
}
  