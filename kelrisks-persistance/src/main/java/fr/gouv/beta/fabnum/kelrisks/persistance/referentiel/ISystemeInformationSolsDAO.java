package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel;

import fr.gouv.beta.fabnum.commun.persistance.IAbstractDAO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.SystemeInformationSols;

import java.util.List;

import org.geolatte.geom.Geometry;

/**
 * Classe interface d'accès DAO à SystemeInformationSols
 */
public interface ISystemeInformationSolsDAO extends IAbstractDAO<SystemeInformationSols> {
    
    List<SystemeInformationSols> rechercherZoneContenantPolygon(Geometry geometry);
}
  