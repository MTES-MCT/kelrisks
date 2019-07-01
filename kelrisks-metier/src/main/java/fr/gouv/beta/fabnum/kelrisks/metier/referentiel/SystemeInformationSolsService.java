package fr.gouv.beta.fabnum.kelrisks.metier.referentiel;

import fr.gouv.beta.fabnum.commun.metier.impl.AbstractCRUDService;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.ISystemeInformationSolsService;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.ISystemeInformationSolsDAO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.impl.SystemeInformationSolsDAO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.SystemeInformationSols;

import java.util.List;

import org.geolatte.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Service métier de gestion de l'entité SystemeInformationSols
 */

@Service("systemeInformationSolsService")
public class SystemeInformationSolsService extends AbstractCRUDService<SystemeInformationSols> implements ISystemeInformationSolsService {
    
    private static final long serialVersionUID = 1L;
    
    /*
     * Le dao spécifique à ce service
     */
    private ISystemeInformationSolsDAO dao;
    
    @Autowired
    public SystemeInformationSolsService(@Qualifier("systemeInformationSolsDAO") final SystemeInformationSolsDAO fdao) {
        
        this.setFdao(fdao);
        dao = fdao;
    }
    
    @Override
    public List<SystemeInformationSols> rechercherZoneContenantPolygon(Geometry geometry) {
        
        return dao.rechercherZoneContenantPolygon(geometry);
    }
}
  