package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel;


import fr.gouv.beta.fabnum.commun.persistance.IAbstractDAO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.Parcelle;

import java.util.List;

import org.geolatte.geom.Geometry;

/**
 * Classe interface d'accès DAO à Parcelle
 */
public interface IParcelleDAO extends IAbstractDAO<Parcelle> {
    
    Parcelle rechercherParcelleContenantPoint(Geometry point);
    
    List<Parcelle> rechercherParcellesContigues(Geometry point);
    
    Parcelle rechercherParcelleAvecCoordonnees(double x, double y);
}
  