package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel;


import fr.gouv.beta.fabnum.commun.persistance.IAbstractDAO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.Parcelle;

import java.util.List;

import org.geolatte.geom.Geometry;

/**
 * Classe interface d'accès DAO à Parcelle
 */
public interface IParcelleDAO extends IAbstractDAO<Parcelle> {
    
    Parcelle rechercherClosestParcelleAvecPoint(Geometry<?> point);
    
    Geometry<?> rechercherExpendedParcelle(Geometry<?> parcelleGeom, double distance);
    
    List<Parcelle> rechercherParcellesContigues(Geometry<?> point);
    
    Parcelle rechercherClosestParcelleAvecCoordonnees(double x, double y);
    
    Geometry<?> rechercherUnionParcellesContigues(Geometry<?> polygon);
    
    Geometry<?> rechercherCentroidParcelle(Geometry<?> polygon);
    
    Geometry<?> rechercherParcellesIntersectionnantSurface(Geometry<?> polygon);
    
    List<Parcelle> rechercherParcellesDansRayon(double x, double y, double radius);
    
    Geometry<?> rechercherUnionParcelles(List<Long> ids);
}
  