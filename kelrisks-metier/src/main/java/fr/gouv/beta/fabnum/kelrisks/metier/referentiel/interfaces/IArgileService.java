package fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces;

import fr.gouv.beta.fabnum.commun.metier.IAbstractCRUDService;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.Argile;

import java.util.List;

import org.geolatte.geom.Geometry;

/**
 * Interface du Service qui gère les entités Argile
 */
public interface IArgileService extends IAbstractCRUDService<Argile> {
    
    List<Argile> rechercherLentillesDansPolygons(List<Geometry<?>> multiPolygon);
    
    List<Argile> rechercherLentillesDansPolygon(Geometry<?> multiPolygon, double distance);
    
    int rechercherNiveauMaximumArgileDansPolygonEtendu(Geometry<?> parcelle, double distance);
}
  