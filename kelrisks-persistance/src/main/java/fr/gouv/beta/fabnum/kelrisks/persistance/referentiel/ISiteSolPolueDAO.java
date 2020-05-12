package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel;


import fr.gouv.beta.fabnum.commun.persistance.IAbstractDAO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.SiteSolPolue;

import java.util.List;

import org.geolatte.geom.Geometry;

/**
 * Classe interface d'accès DAO à SiteSolPolue
 */
public interface ISiteSolPolueDAO extends IAbstractDAO<SiteSolPolue> {
    
    List<SiteSolPolue> rechercherZoneContenantParcelle(Geometry<?> parcelleGeog);
    
    List<SiteSolPolue> rechercherZoneContenantPolygon(Geometry<?> geometry);
}
  