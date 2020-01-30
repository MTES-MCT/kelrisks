package fr.gouv.beta.fabnum.kelrisks.metier.referentiel;

import fr.gouv.beta.fabnum.commun.metier.impl.AbstractCRUDService;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.IPlanPreventionRisquesGasparService;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.IPlanPreventionRisquesGasparDAO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.impl.PlanPreventionRisquesGasparDAO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.PlanPreventionRisquesGaspar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Service métier de gestion de l'entité PlanPreventionRisquesGaspar
 */

@Service("planPreventionRisquesGasparService")
public class PlanPreventionRisquesGasparService extends AbstractCRUDService<PlanPreventionRisquesGaspar> implements IPlanPreventionRisquesGasparService {
    
    private static final long serialVersionUID = 1L;
    
    /*
     * Le dao spécifique à ce service
     */
    private IPlanPreventionRisquesGasparDAO dao;
    
    @Autowired
    public PlanPreventionRisquesGasparService(@Qualifier("planPreventionRisquesGasparDAO") final PlanPreventionRisquesGasparDAO fdao) {
        
        this.setFdao(fdao);
        dao = fdao;
    }
}
  