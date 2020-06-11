package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel;

import fr.gouv.beta.fabnum.commun.persistance.IAbstractDAO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.Argile;

import java.util.List;

import org.geolatte.geom.Geometry;

/**
 * Classe interface d'accès DAO à Argile
 */
public interface IArgileDAO extends IAbstractDAO<Argile> {
    
    List<Argile> rechercherLentillesDansPolygons(List<Geometry<?>> multiPolygon);
    
    List<Argile> rechercherLentillesDansPolygon(Geometry<?> multiPolygon, double distance);
    
    Integer rechercherNiveauMaximumArgileDansPolygonEtendu(Geometry<?> parcelle, double distance);
}
  