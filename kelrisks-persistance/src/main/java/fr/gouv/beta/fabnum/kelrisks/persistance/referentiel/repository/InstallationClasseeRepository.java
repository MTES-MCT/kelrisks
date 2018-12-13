package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.repository;


import fr.gouv.beta.fabnum.commun.persistance.IAbstractRepository;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.InstallationClassee;

import java.util.List;

import org.geolatte.geom.Geometry;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;

/**
 * Un repository pour InstallationClassee
 * Cette classe est utilisé par Spring data, elle facilite la réalisation des DAO en diminuant le code à écrire.
 * Chaque DAO fait appel au repository associé pour executer ses requêtes JPA (Hibernate)
 */
@Qualifier("installationClasseeRepository")
public interface InstallationClasseeRepository extends IAbstractRepository<InstallationClassee> {
    
    
    @Query(value = "SELECT ic " +
                   "FROM InstallationClassee ic, Parcelle p " +
                   "WHERE p.code = :codeParcelle" +
                   "  AND st_within(ic.point, p.multiPolygon) = TRUE")
    List<InstallationClassee> rechercherInstallationsSurParcelle(String codeParcelle);
    
    @Query("SELECT ic " +
           "FROM InstallationClassee AS ic " +
           "WHERE st_dwithin(ic.point, " +
           "                 st_centroid((SELECT p.multiPolygon " +
           "                             FROM Parcelle AS p " +
           "                             WHERE p.code = :codeParcelle)), " +
           "                 :distance) = TRUE")
    List<InstallationClassee> rechercherInstallationsDansRayonCentroideParcelle(String codeParcelle, double distance);
    
    @Query(value = "SELECT ic " +
                   "FROM InstallationClassee ic " +
                   "WHERE st_within(ic.point, :multiPolygon) = TRUE")
    List<InstallationClassee> rechercherSitesDansPolygon(Geometry multiPolygon);
}
  