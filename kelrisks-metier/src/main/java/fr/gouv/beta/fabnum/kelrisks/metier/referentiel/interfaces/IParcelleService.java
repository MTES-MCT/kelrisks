package fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces;


import fr.gouv.beta.fabnum.commun.metier.IAbstractCRUDService;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.Parcelle;

import org.geolatte.geom.Geometry;

/**
 * Interface du Service qui gère les entités Parcelle
 */
public interface IParcelleService extends IAbstractCRUDService<Parcelle> {
    
    Parcelle rechercherParcelleContenantPoint(Geometry point);
}
  