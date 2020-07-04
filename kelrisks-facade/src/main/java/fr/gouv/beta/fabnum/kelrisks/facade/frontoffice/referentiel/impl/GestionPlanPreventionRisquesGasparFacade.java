package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.impl;

import fr.gouv.beta.fabnum.commun.facade.AbstractFacade;
import fr.gouv.beta.fabnum.commun.transverse.qo.CritereTri;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.PlanPreventionRisquesGasparDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionPlanPreventionRisquesGasparFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.referentiel.IPlanPreventionRisquesGasparMapper;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.IPlanPreventionRisquesGasparService;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo.PlanPreventionRisquesGasparQO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestionPlanPreventionRisquesGasparFacade extends AbstractFacade implements IGestionPlanPreventionRisquesGasparFacade {
    
    @Autowired
    IPlanPreventionRisquesGasparMapper  planPreventionRisquesGasparMapper;
    @Autowired
    IPlanPreventionRisquesGasparService planPreventionRisquesGasparService;
    
    @Override
    public PlanPreventionRisquesGasparDTO rechercherResultatUniqueAvecCritere(PlanPreventionRisquesGasparQO planPreventionRisquesGasparQO) {
        
        PlanPreventionRisquesGasparDTO planPreventionRisquesGasparDTO =
                planPreventionRisquesGasparMapper.toDTO(planPreventionRisquesGasparService.rechercherResultatUniqueAvecCritere(planPreventionRisquesGasparQO));
        
        return planPreventionRisquesGasparDTO;
    }
    
    @Override
    public List<PlanPreventionRisquesGasparDTO> rechercherAvecCritere(PlanPreventionRisquesGasparQO planPreventionRisquesGasparQO) {
    
        CritereTri datePrescription = new CritereTri("datePrescription", false);
    
        List<PlanPreventionRisquesGasparDTO> planPreventionRisquesGasparDTOs =
                planPreventionRisquesGasparMapper.toDTOs(planPreventionRisquesGasparService.rechercherAvecCritere(datePrescription, planPreventionRisquesGasparQO));
    
        return planPreventionRisquesGasparDTOs;
    }
}
