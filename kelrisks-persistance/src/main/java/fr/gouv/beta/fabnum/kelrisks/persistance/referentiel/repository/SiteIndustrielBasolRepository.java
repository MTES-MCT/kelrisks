package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.repository;


import fr.gouv.beta.fabnum.commun.persistance.IAbstractRepository;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.SiteIndustrielBasol;

import java.util.List;

import org.geolatte.geom.Geometry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 * Un repository pour SiteIndustrielBasol
 * Cette classe est utilisé par Spring data, elle facilite la réalisation des DAO en diminuant le code à écrire.
 * Chaque DAO fait appel au repository associé pour executer ses requêtes JPA (Hibernate)
 */
public interface SiteIndustrielBasolRepository extends IAbstractRepository<SiteIndustrielBasol> {
    
    @Query(value = "SELECT si " +
                   "FROM SiteIndustrielBasol si, Parcelle p " +
                   "WHERE p.code = :codeParcelle" +
                   "  AND st_within(si.point, p.multiPolygon) = TRUE")
    List<SiteIndustrielBasol> rechercherSiteSurParcelle(@Param("codeParcelle") String codeParcelle);
    
    @Query("SELECT si " +
           "FROM SiteIndustrielBasol AS si " +
           "WHERE st_dwithin(si.point, " +
           "                 st_centroid((SELECT p.multiPolygon " +
           "                             FROM Parcelle AS p " +
           "                             WHERE p.code = :codeParcelle)), " +
           "                 :distance) = TRUE")
    List<SiteIndustrielBasol> rechercherSiteDansRayonCentroideParcelle(@Param("codeParcelle") String codeParcelle,
                                                                       @Param("distance") double distance);
    
    @Query(value = "SELECT sib " +
                   "FROM SiteIndustrielBasol sib " +
                   "WHERE st_within(sib.point, :multiPolygon) = TRUE")
    List<SiteIndustrielBasol> rechercherSitesDansPolygon(Geometry multiPolygon);
    
    @Query(value = "SELECT sib " +
                   "FROM SiteIndustrielBasol sib " +
                   "WHERE st_within(sib.point, st_union(:multiPolygon)) = TRUE")
    List<SiteIndustrielBasol> rechercherSitesDansPolygons(List<Geometry> multiPolygon);
    
    @Query(value = "SELECT si " +
                   "FROM SiteIndustrielBasol si, Parcelle p " +
                   "WHERE p.code IN :codes" +
                   "  AND st_within(si.point, p.multiPolygon) = TRUE")
    List<SiteIndustrielBasol> rechercherSitesSurParcelles(List<String> codes);
}

