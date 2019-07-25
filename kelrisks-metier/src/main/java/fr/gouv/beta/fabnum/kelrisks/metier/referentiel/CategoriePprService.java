package fr.gouv.beta.fabnum.kelrisks.metier.referentiel;

import fr.gouv.beta.fabnum.commun.metier.impl.AbstractCRUDService;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.ICategoriePprService;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.ICategoriePprDAO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.impl.CategoriePprDAO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.CategoriePPR;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Service métier de gestion de l'entité CategoriePPR
 */

@Service("categoriePprService")
public class CategoriePprService extends AbstractCRUDService<CategoriePPR> implements ICategoriePprService {
    
    private static final long serialVersionUID = 1L;
    
    /*
     * Le dao spécifique à ce service
     */
    private ICategoriePprDAO dao;
    
    @Autowired
    public CategoriePprService(@Qualifier("categoriePprDAO") final CategoriePprDAO fdao) {
        
        this.setFdao(fdao);
        dao = fdao;
    }
}
  