package fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces;

import fr.gouv.beta.fabnum.commun.metier.IAbstractCRUDService;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.SystemeInformationSols;

import java.util.List;

import org.geolatte.geom.Geometry;

/**
 * Interface du Service qui gère les entités SystemeInformationSols
 */
public interface ISystemeInformationSolsService extends IAbstractCRUDService<SystemeInformationSols> {
    
    List<SystemeInformationSols> rechercherZoneContenantPolygon(Geometry geometry);
}
  