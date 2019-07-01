package fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces;


import fr.gouv.beta.fabnum.commun.metier.IAbstractCRUDService;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.SiteSolPolue;

import java.util.List;

import org.geolatte.geom.Geometry;

/**
 * Interface du Service qui gère les entités SiteSolPolue
 */
public interface ISiteSolPolueService extends IAbstractCRUDService<SiteSolPolue> {
    
    List<SiteSolPolue> rechercherZoneContenantParcelle(String codeParcelle);
    
    List<SiteSolPolue> rechercherZoneContenantPolygon(Geometry geometry);
}
  