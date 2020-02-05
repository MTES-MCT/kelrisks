package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel;

import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.PlanPreventionRisquesDTO;

import java.util.List;

import org.geolatte.geom.Geometry;

public interface IGestionPlanPreventionRisquesFacade {
    
    List<PlanPreventionRisquesDTO> rechercherSitesDansPolygons(List<Geometry<?>> multiPolygons);
    
    List<PlanPreventionRisquesDTO> rechercherSitesDansPolygon(Geometry<?> multiPolygons);
}