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
    
    @Query(value = "SELECT * " +
                   " FROM kelrisks.s3ic ic, " +
                   "      (SELECT p.geog FROM kelrisks.cadastre AS p WHERE p.code = :codeParcelle) polygon " +
                   " WHERE st_intersects(ic.geog, polygon) OR " +
                   "       st_intersects(ic.geog, polygon) ", nativeQuery = true)
    List<InstallationClassee> rechercherInstallationsSurParcelle(String codeParcelle);
    
    @Query(value = "SELECT * " +
                   " FROM kelrisks.s3ic ic, " +
                   "      st_centroid((SELECT p.geog FROM kelrisks.cadastre AS p  WHERE p.code = :codeParcelle)) centroide" +
                   " WHERE st_dwithin(ic.geog, centroide, :distance) OR " +
                   "       st_dwithin(ic.geog, centroide, :distance)", nativeQuery = true)
    List<InstallationClassee> rechercherInstallationsDansRayonCentroideParcelle(String codeParcelle, double distance);
    
    @Query(value = "SELECT * " +
                   " FROM kelrisks.s3ic ic " +
                   " WHERE st_intersects(ic.geog, :multiPolygon) OR " +
                   "       st_intersects(ic.geog, :multiPolygon)", nativeQuery = true)
    List<InstallationClassee> rechercherSitesDansPolygon(Geometry<?> multiPolygon);
    
    @Query(value = "SELECT * " +
                   " FROM kelrisks.s3ic ic " +
                   " WHERE st_intersects(ic.geog, st_union(:multiPolygon)) OR " +
                   "       st_intersects(ic.geog, st_union(:multiPolygon))", nativeQuery = true)
    List<InstallationClassee> rechercherSitesDansPolygons(List<Geometry<?>> multiPolygon);
    
    @Query(value = "SELECT * " +
                   " FROM kelrisks.s3ic ic, " +
                   "      (SELECT st_union(p.geog) FROM kelrisks.cadastre p WHERE p.code IN :codes) polygon " +
                   " WHERE st_intersects(ic.geog, polygon) OR " +
                   "       st_intersects(ic.geog, polygon) ", nativeQuery = true)
    List<InstallationClassee> rechercherInstallationsSurParcelles(List<String> codes);
    
    @Query(value = "SELECT * " +
                   " FROM kelrisks.s3ic AS si" +
                   " WHERE si.id IN (SELECT min(siiic.id) FROM kelrisks.s3ic AS siiic WHERE lower(siiic.nom) LIKE concat('%', lower(:query) , '%') AND siiic.code_insee = :codeINSEE" +
                   " GROUP BY siiic.nom)", nativeQuery = true)
    List<InstallationClassee> rechercherRaisonsSociales(String codeINSEE, String query);
}
  