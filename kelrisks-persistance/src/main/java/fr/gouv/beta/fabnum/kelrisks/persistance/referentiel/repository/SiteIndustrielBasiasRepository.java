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
    
    @Query(value = "SELECT * " +
                   " FROM kelrisks.basias si" +
                   " LEFT JOIN kelrisks.cadastre AS p ON st_intersects(si.geog, p.geog)" +
                   " WHERE p.code = :codeParcelle", nativeQuery = true)
    List<SiteIndustrielBasias> rechercherSiteSurParcelle(@Param("codeParcelle") String codeParcelle);
    
    @Query(value = "SELECT * " +
                   " FROM kelrisks.basias AS si" +
                   " WHERE st_intersects(si.geog," +
                   " st_centroid(:geom)," +
                   " :distance)", nativeQuery = true)
    List<SiteIndustrielBasias> rechercherSiteDansRayonCentroideParcelle(@Param("geom") Geometry geom,
                                                                        @Param("distance") double distance);
    
    @Query(value = "SELECT sib " +
                   " FROM SiteIndustrielBasias AS sib" +
                   " WHERE st_intersects(sib.multiPolygon, st_union(:multiPolygon)) = TRUE")
    List<SiteIndustrielBasias> rechercherSitesDansPolygons(List<Geometry> multiPolygon);
    
    @Query(value = "SELECT sib " +
                   " FROM SiteIndustrielBasias AS sib" +
                   " WHERE st_intersects(sib.multiPolygon, :multiPolygon) = TRUE")
    List<SiteIndustrielBasias> rechercherSitesDansPolygon(Geometry multiPolygon);
    
    @Query(value = "SELECT * " +
                   " FROM kelrisks.basias AS si" +
                   " WHERE st_dwithin(si.geog, st_centroid(:geometry), :distance)" +
                   " AND lower(si.raison_sociale) LIKE lower(concat('%',:nomProprietaire,'%'))", nativeQuery = true)
    List<SiteIndustrielBasias> rechercherParNomProprietaireDansRayonGeometry(Geometry geometry, String nomProprietaire, double distance);
    
    @Query(value = "SELECT * " +
                   " FROM kelrisks.basias AS si" +
                   " WHERE si.id IN (SELECT min(b.id) FROM kelrisks.basias AS b WHERE lower(b.raison_sociale) LIKE concat('%', lower(:query) , '%')" +
                   " GROUP BY b.raison_sociale)", nativeQuery = true)
    List<SiteIndustrielBasias> rechercherRaisonsSociales(String query);
    
    @Query(value = "SELECT * " +
                   " FROM kelrisks.basias si" +
                   " LEFT JOIN kelrisks.cadastre AS p ON st_intersects(si.geog, p.geog)" +
                   " WHERE p.code in :codes", nativeQuery = true)
    List<SiteIndustrielBasias> rechercherSitesSurParcelles(List<String> codes);
}