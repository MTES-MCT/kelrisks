package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel;


import fr.gouv.beta.fabnum.commun.persistance.IAbstractDAO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.InstallationClassee;

import java.util.List;

import org.geolatte.geom.Geometry;

/**
 * Classe interface d'accès DAO à InstallationClassee
 */
public interface IInstallationClasseeDAO extends IAbstractDAO<InstallationClassee> {
    
    List<InstallationClassee> rechercherInstallationsSurParcelle(String codeParcelle);
    
    List<InstallationClassee> rechercherInstallationsDansRayonCentroideParcelle(String codeParcelle, double distance);
    
    List<InstallationClassee> rechercherSitesDansPolygons(List<Geometry<?>> multiPolygon);
    
    List<InstallationClassee> rechercherInstallationsSurParcelles(List<String> codes);
    
    List<InstallationClassee> rechercherRaisonsSociales(String codeINSEE, String query);
    
    List<InstallationClassee> rechercherSitesDansPolygon(Geometry<?> polygon);
}
  