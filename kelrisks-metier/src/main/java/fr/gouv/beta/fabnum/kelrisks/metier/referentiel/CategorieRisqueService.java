package fr.gouv.beta.fabnum.kelrisks.metier.referentiel;

import fr.gouv.beta.fabnum.commun.metier.impl.AbstractCRUDService;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.ICategorieRisqueService;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.ICategorieRisqueDAO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.impl.CategorieRisqueDAO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.CategorieRisque;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Service métier de gestion de l'entité CategorieRisque
 */

@Service("categorieRisqueService")
public class CategorieRisqueService extends AbstractCRUDService<CategorieRisque> implements ICategorieRisqueService {
    
    private static final long serialVersionUID = 1L;
    
    /*
     * Le dao spécifique à ce service
     */
    private ICategorieRisqueDAO dao;
    
    @Autowired
    public CategorieRisqueService(@Qualifier("categorieRisqueDAO") final CategorieRisqueDAO fdao) {
        
        this.setFdao(fdao);
        dao = fdao;
    }
}
  