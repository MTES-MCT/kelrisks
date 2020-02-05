package fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces;

import fr.gouv.beta.fabnum.commun.metier.IAbstractCRUDService;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.PlanPreventionRisques;

import java.util.List;

import org.geolatte.geom.Geometry;

/**
 * Interface du Service qui gère les entités PlanPreventionRisques
 */
public interface IPlanPreventionRisquesService extends IAbstractCRUDService<PlanPreventionRisques> {
    
    List<PlanPreventionRisques> rechercherSitesDansPolygons(List<Geometry<?>> multiPolygon);
    
    List<PlanPreventionRisques> rechercherSitesDansPolygon(Geometry<?> multiPolygon);
}
  