package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.repository;


import fr.gouv.beta.fabnum.commun.persistance.IAbstractRepository;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.SiteIndustrielBasias;

import java.util.List;

import org.geolatte.geom.Geometry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Un repository pour SiteIndustrielBasias
 * Cette classe est utilisé par Spring data, elle facilite la réalisation des DAO en diminuant le code à écrire.
 * Chaque DAO fait appel au repository associé pour executer ses requêtes JPA (Hibernate)
 */
public interface SiteIndustrielBasiasRepository extends IAbstractRepository<SiteIndustrielBasias> {
    
    @Query(value = "SELECT si " +
                   "FROM SiteIndustrielBasias si, Parcelle p " +
                   "WHERE p.code = :codeParcelle" +
                   "  AND st_within(si.point, p.multiPolygon) = TRUE")
    List<SiteIndustrielBasias> rechercherSiteSurParcelle(@Param("codeParcelle") String codeParcelle);
    
    @Query("SELECT si " +
           "FROM SiteIndustrielBasias AS si " +
           "WHERE st_within(si.point, " +
           "                 st_centroid((SELECT p.multiPolygon " +
           "                             FROM Parcelle AS p " +
           "                             WHERE p.code = :codeParcelle)), " +
           "                 :distance) = TRUE")
    List<SiteIndustrielBasias> rechercherSiteDansRayonCentroideParcelle(@Param("codeParcelle") String codeParcelle,
                                                                        @Param("distance") double distance);
    
    @Query(value = "SELECT si " +
                   "FROM SiteIndustrielBasias si " +
                   "WHERE st_within(si.point, :multiPolygon) = TRUE")
    List<SiteIndustrielBasias> rechercherSitesDansPolygon(Geometry multiPolygon);
}