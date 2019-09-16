package fr.gouv.beta.fabnum.kelrisks.metier.referentiel;

import fr.gouv.beta.fabnum.commun.metier.impl.AbstractCRUDService;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.IFamillePprService;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.IFamillePprDAO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.impl.FamillePprDAO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.FamillePPR;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Service métier de gestion de l'entité FamillePPR
 */

@Service("famillePprService")
public class FamillePprService extends AbstractCRUDService<FamillePPR> implements IFamillePprService {
    
    private static final long serialVersionUID = 1L;
    
    /*
     * Le dao spécifique à ce service
     */
    private IFamillePprDAO dao;
    
    @Autowired
    public FamillePprService(@Qualifier("famillePprDAO") final FamillePprDAO fdao) {
        
        this.setFdao(fdao);
        dao = fdao;
    }
}
  