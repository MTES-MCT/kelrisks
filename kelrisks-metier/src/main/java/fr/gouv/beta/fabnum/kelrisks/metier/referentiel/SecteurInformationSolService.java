package fr.gouv.beta.fabnum.kelrisks.metier.referentiel;

import fr.gouv.beta.fabnum.commun.metier.impl.AbstractCRUDService;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.ISecteurInformationSolService;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.ISecteurInformationSolDAO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.impl.SecteurInformationSolDAO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.SecteurInformationSol;

import java.util.List;

import org.geolatte.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Service métier de gestion de l'entité SecteurInformationSol
 */

@Service("secteurInformationSolService")
public class SecteurInformationSolService extends AbstractCRUDService<SecteurInformationSol> implements ISecteurInformationSolService {
    
    private static final long serialVersionUID = 1L;
    
    /*
     * Le dao spécifique à ce service
     */
    private ISecteurInformationSolDAO dao;
    
    @Autowired
    public SecteurInformationSolService(@Qualifier("secteurInformationSolDAO") final SecteurInformationSolDAO fdao) {
        
        this.setFdao(fdao);
        dao = fdao;
    }
    
    @Override
    public List<SecteurInformationSol> rechercherSecteursDansPolygon(Geometry<?> geometry) {
    
        return dao.rechercherSecteursDansPolygon(geometry);
    }
    
    @Override
    public List<SecteurInformationSol> rechercherSecteursDansPolygons(List<Geometry<?>> geometries) {
        
        return dao.rechercherSecteursDansPolygons(geometries);
    }
}
  