package fr.gouv.beta.fabnum.kelrisks.metier.referentiel;

import fr.gouv.beta.fabnum.commun.metier.impl.AbstractCRUDService;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.IPlanExpositionBruitService;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.IPlanExpositionBruitDAO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.impl.PlanExpositionBruitDAO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.PlanExpositionBruit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Service métier de gestion de l'entité PlanExpositionBruit
 */

@Service("planExpositionBruitService")
public class PlanExpositionBruitService extends AbstractCRUDService<PlanExpositionBruit> implements IPlanExpositionBruitService {
    
    private static final long serialVersionUID = 1L;
    
    /*
     * Le dao spécifique à ce service
     */
    private IPlanExpositionBruitDAO dao;
    
    @Autowired
    public PlanExpositionBruitService(@Qualifier("planExpositionBruitDAO") final PlanExpositionBruitDAO fdao) {
        
        this.setFdao(fdao);
        dao = fdao;
    }
}
  