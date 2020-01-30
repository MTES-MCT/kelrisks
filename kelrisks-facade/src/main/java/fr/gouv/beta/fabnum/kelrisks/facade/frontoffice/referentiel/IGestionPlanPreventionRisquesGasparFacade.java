package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel;

import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.PlanPreventionRisquesGasparDTO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo.PlanPreventionRisquesGasparQO;

import java.util.List;

public interface IGestionPlanPreventionRisquesGasparFacade {
    
    PlanPreventionRisquesGasparDTO rechercherResultatUniqueAvecCritere(PlanPreventionRisquesGasparQO planPreventionRisquesGasparQO);
    
    List<PlanPreventionRisquesGasparDTO> rechercherAvecCritere(PlanPreventionRisquesGasparQO planPreventionRisquesGasparQO);
}