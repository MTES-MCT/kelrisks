package fr.gouv.beta.fabnum.kelrisks.metier.referentiel;

import fr.gouv.beta.fabnum.commun.metier.impl.AbstractCRUDService;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.IParcelleService;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.IParcelleDAO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.impl.ParcelleDAO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.Parcelle;

import org.geolatte.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Service métier de gestion de l'entité Parcelle
 */

@Service("parcelleService")
public class ParcelleService extends AbstractCRUDService<Parcelle> implements IParcelleService {
    
    private static final long serialVersionUID = 1L;
    
    /*
     * Le dao spécifique à ce service
     */
    private IParcelleDAO dao;
    
    @Autowired
    public ParcelleService(@Qualifier("parcelleDAO") final ParcelleDAO fdao) {
        
        this.setFdao(fdao);
        dao = fdao;
    }
    
    @Override
    public Parcelle rechercherParcelleContenantPoint(Geometry point) {
        
        return dao.rechercherParcelleContenantPoint(point);
    }
}
  