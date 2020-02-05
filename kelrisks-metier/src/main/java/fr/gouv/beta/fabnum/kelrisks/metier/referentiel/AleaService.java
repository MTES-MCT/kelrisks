package fr.gouv.beta.fabnum.kelrisks.metier.referentiel;

import fr.gouv.beta.fabnum.commun.metier.impl.AbstractCRUDService;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.IAleaService;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.IAleaDAO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.impl.AleaDAO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.Alea;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Service métier de gestion de l'entité Alea
 */

@Service("aleaService")
public class AleaService extends AbstractCRUDService<Alea> implements IAleaService {
    
    private static final long serialVersionUID = 1L;
    
    /*
     * Le dao spécifique à ce service
     */
    private IAleaDAO dao;
    
    @Autowired
    public AleaService(@Qualifier("aleaDAO") final AleaDAO fdao) {
        
        this.setFdao(fdao);
        dao = fdao;
    }
}
  