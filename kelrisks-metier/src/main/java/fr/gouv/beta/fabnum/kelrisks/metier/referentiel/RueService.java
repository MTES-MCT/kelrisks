package fr.gouv.beta.fabnum.kelrisks.metier.referentiel;

import fr.gouv.beta.fabnum.commun.metier.impl.AbstractCRUDService;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.IRueService;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.IRueDAO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.impl.RueDAO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.Rue;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Service métier de gestion de l'entité Rue
 */

@Service("rueService")
public class RueService extends AbstractCRUDService<Rue> implements IRueService {
    
    private static final long serialVersionUID = 1L;
    
    /*
     * Le dao spécifique à ce service
     */
    private IRueDAO dao;
    
    @Autowired
    public RueService(@Qualifier("rueDAO") final RueDAO fdao) {
        
        this.setFdao(fdao);
        dao = fdao;
    }
}
  