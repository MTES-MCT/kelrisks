package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel;

import fr.gouv.beta.fabnum.commun.persistance.IAbstractDAO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.PlanExpositionBruit;

import java.util.List;

import org.geolatte.geom.Point;

/**
 * Classe interface d'accès DAO à PlanExpositionBruit
 */
public interface IPlanExpositionBruitDAO extends IAbstractDAO<PlanExpositionBruit> {
    
    String rechercherZoneCentroid(Point<?> centroid);
    
    List<PlanExpositionBruit> rechercherPlanExpositionBruitDansRayon(Point<?> centroid, double distance);
}
  