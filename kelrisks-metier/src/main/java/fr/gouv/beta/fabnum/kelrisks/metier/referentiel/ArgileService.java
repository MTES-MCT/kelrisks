package fr.gouv.beta.fabnum.kelrisks.metier.referentiel;

import fr.gouv.beta.fabnum.commun.metier.impl.AbstractCRUDService;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.IArgileService;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.IArgileDAO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.impl.ArgileDAO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.Argile;

import java.util.ArrayList;
import java.util.List;

import org.geolatte.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Service métier de gestion de l'entité Argile
 */

@Service("argileService")
public class ArgileService extends AbstractCRUDService<Argile> implements IArgileService {
    
    private static final long serialVersionUID = 1L;
    
    /*
     * Le dao spécifique à ce service
     */
    private IArgileDAO dao;
    
    @Autowired
    public ArgileService(@Qualifier("argileDAO") final ArgileDAO fdao) {
        
        this.setFdao(fdao);
        dao = fdao;
    }
    
    @Override
    public List<Argile> rechercherLentillesDansPolygons(List<Geometry<?>> multiPolygon) {
    
        if (multiPolygon == null || multiPolygon.isEmpty()) { return new ArrayList<>(); }
    
        return dao.rechercherLentillesDansPolygons(multiPolygon);
    }
    
    @Override
    public List<Argile> rechercherLentillesDansPolygon(Geometry<?> polygon, double distance) {
        
        if (polygon == null || polygon.isEmpty()) { return new ArrayList<>(); }
        
        return dao.rechercherLentillesDansPolygon(polygon, distance);
    }
    
    @Override
    public int rechercherNiveauMaximumArgileDansPolygonEtendu(Geometry<?> polygon, double distance) {
        
        Integer niveau = dao.rechercherNiveauMaximumArgileDansPolygonEtendu(polygon, distance);
        return niveau == null ? 0 : niveau;
    }
}
  