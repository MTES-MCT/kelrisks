package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.impl;

import fr.gouv.beta.fabnum.commun.facade.AbstractFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.PlanExpositionBruitDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionPlanExpositionBruitFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.referentiel.IPlanExpositionBruitMapper;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.IPlanExpositionBruitService;

import java.util.List;

import org.geolatte.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestionPlanExpositionBruitFacade extends AbstractFacade implements IGestionPlanExpositionBruitFacade {
    
    @Autowired
    IPlanExpositionBruitMapper  planExpositionBruitMapper;
    @Autowired
    IPlanExpositionBruitService planExpositionBruitService;
    
    @Override
    public String rechercherZoneCentroid(Point<?> centroid) {
        
        return planExpositionBruitService.rechercherZoneCentroid(centroid);
    }
    
    @Override
    public List<PlanExpositionBruitDTO> rechercherPlanExpositionBruitDansRayon(Point<?> centroid, double distance) {
        
        return planExpositionBruitMapper.toDTOs(planExpositionBruitService.rechercherPlanExpositionBruitDansRayon(centroid, distance));
    }
}