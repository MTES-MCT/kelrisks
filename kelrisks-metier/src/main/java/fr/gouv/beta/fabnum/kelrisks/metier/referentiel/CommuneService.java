package fr.gouv.beta.fabnum.kelrisks.metier.referentiel;

import fr.gouv.beta.fabnum.commun.metier.impl.AbstractCRUDService;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.ICommuneService;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.ICommuneDAO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.impl.CommuneDAO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.Commune;

import java.util.List;

import org.geolatte.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Service métier de gestion de l'entité Commune
 */

@Service("communeService")
public class CommuneService extends AbstractCRUDService<Commune> implements ICommuneService {
    
    private static final long serialVersionUID = 1L;
    
    /*
     * Le dao spécifique à ce service
     */
    private ICommuneDAO dao;
    
    @Autowired
    public CommuneService(@Qualifier("communeDAO") final CommuneDAO fdao) {
        
        this.setFdao(fdao);
        dao = fdao;
    }
    
    @Override
    public List<Commune> rechercherCommunesLimitrophes(Geometry<?> geog, String notINSEE) {
        
        return dao.rechercherCommunesLimitrophes(geog, notINSEE);
    }
}
  