package fr.gouv.beta.fabnum.kelrisks.metier.referentiel;

import fr.gouv.beta.fabnum.commun.metier.impl.AbstractCRUDService;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.IParcelleService;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.IParcelleDAO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.impl.ParcelleDAO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.Parcelle;

import java.util.List;

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
    public Parcelle rechercherClosestParcelleAvecPoint(Geometry<?> point) {
    
        return dao.rechercherClosestParcelleAvecPoint(point);
    }
    
    @Override
    public List<Parcelle> rechercherParcellesContigues(Geometry<?> geom) {
        
        return dao.rechercherParcellesContigues(geom);
    }
    
    @Override
    public Parcelle rechercherClosestParcelleAvecCoordonnees(double x, double y) {
    
        return dao.rechercherClosestParcelleAvecCoordonnees(x, y);
    }
    
    @Override
    public Geometry<?> rechercherExpendedParcelle(Geometry<?> parcelleGeog, double distance) {
    
        return dao.rechercherExpendedParcelle(parcelleGeog, distance);
    }
    
    @Override
    public Geometry<?> rechercherUnionParcellesContigues(Geometry<?> polygon) {
        
        return dao.rechercherUnionParcellesContigues(polygon);
    }
    
    @Override
    public Geometry<?> rechercherCentroidParcelle(Geometry<?> polygon) {
        
        return dao.rechercherCentroidParcelle(polygon);
    }
    
    @Override
    public Geometry<?> rechercherParcellesIntersectionnantSurface(Geometry<?> polygon) {
    
        return dao.rechercherParcellesIntersectionnantSurface(polygon);
    }
    
    @Override
    public List<Parcelle> rechercherParcellesDansRayon(double x, double y, double radius) {
        
        return dao.rechercherParcellesDansRayon(x, y, radius);
    }
    
    @Override
    public Geometry<?> rechercherUnionParcelles(List<Long> ids) {
        
        return dao.rechercherUnionParcelles(ids);
    }
}
  