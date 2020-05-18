package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.impl;

import fr.gouv.beta.fabnum.commun.facade.AbstractFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionPlanExpositionBruitFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.refentiel.IPlanExpositionBruitMapper;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.IPlanExpositionBruitService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestionPlanExpositionBruitFacade extends AbstractFacade implements IGestionPlanExpositionBruitFacade {
    
    @Autowired
    IPlanExpositionBruitMapper  planExpositionBruitMapper;
    @Autowired
    IPlanExpositionBruitService planExpositionBruitService;
}
