package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.impl;

import fr.gouv.beta.fabnum.commun.facade.AbstractFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.PlanPreventionRisquesDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionPlanPreventionRisquesFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.refentiel.IPlanPreventionRisquesMapper;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.IParcelleService;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.IPlanPreventionRisquesService;

import java.util.List;

import org.geolatte.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestionPlanPreventionRisquesFacade extends AbstractFacade implements IGestionPlanPreventionRisquesFacade {
    
    @Autowired
    IPlanPreventionRisquesService siteIndustrielService;
    @Autowired
    IParcelleService              parcelleService;
    @Autowired
    IPlanPreventionRisquesMapper  siteIndustrielMapper;
    
    @Override
    public List<PlanPreventionRisquesDTO> rechercherSitesDansPolygons(List<Geometry> multiPolygon) {
        
        List<PlanPreventionRisquesDTO> planPreventionRisquesDTOs = siteIndustrielMapper.toDTOs(siteIndustrielService.rechercherSitesDansPolygons(multiPolygon));
        
        return planPreventionRisquesDTOs;
    }
    
    @Override
    public List<PlanPreventionRisquesDTO> rechercherSitesDansPolygon(Geometry polygon) {
        
        List<PlanPreventionRisquesDTO> planPreventionRisquesDTOs = siteIndustrielMapper.toDTOs(siteIndustrielService.rechercherSitesDansPolygon(polygon));
        
        return planPreventionRisquesDTOs;
    }
}
