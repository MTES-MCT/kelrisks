package fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces;


import fr.gouv.beta.fabnum.commun.metier.IAbstractCRUDService;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.InstallationClassee;

import java.util.List;

import org.geolatte.geom.Geometry;

/**
 * Interface du Service qui gère les entités InstallationClassee
 */
public interface IInstallationClasseeService extends IAbstractCRUDService<InstallationClassee> {
    
    List<InstallationClassee> rechercherInstallationsSurParcelle(String codeParcelle);
    
    List<InstallationClassee> rechercherInstallationsDansRayonCentroideParcelle(String codeParcelle, double distance);
    
    List<InstallationClassee> rechercherSitesDansPolygon(Geometry multiPolygon);
}
  