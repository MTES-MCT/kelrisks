package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel;

import fr.gouv.beta.fabnum.commun.persistance.IAbstractDAO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.PlanPreventionRisques;

import java.util.List;

import org.geolatte.geom.Geometry;

/**
 * Classe interface d'accès DAO à PlanPreventionRisques
 */
public interface IPlanPreventionRisquesDAO extends IAbstractDAO<PlanPreventionRisques> {
    
    List<PlanPreventionRisques> rechercherSitesDansPolygons(List<Geometry> multiPolygon);
    
    List<PlanPreventionRisques> rechercherSitesDansPolygon(Geometry multiPolygon);
}
  