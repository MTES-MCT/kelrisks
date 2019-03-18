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
                   " LEFT JOIN kelrisks.cadastre AS p ON st_within(si.geog, p.geog)" +
                   " WHERE p.code = :codeParcelle", nativeQuery = true)
    List<SiteIndustrielBasias> rechercherSiteSurParcelle(@Param("codeParcelle") String codeParcelle);
    
    @Query(value = "SELECT * " +
                   " FROM kelrisks.basias AS si" +
                   " WHERE st_dwithin(si.geog," +
                   " st_centroid((SELECT p.geog FROM kelrisks.cadastre AS p WHERE p.code = :codeParcelle))," +
                   " :distance)", nativeQuery = true)
    List<SiteIndustrielBasias> rechercherSiteDansRayonCentroideParcelle(@Param("codeParcelle") String codeParcelle,
                                                                        @Param("distance") double distance);
    
    @Query(value = "SELECT * " +
                   " FROM kelrisks.basias AS si" +
                   " WHERE st_within(si.geog, :geog)", nativeQuery = true)
    List<SiteIndustrielBasias> rechercherSitesDansPolygon(Geometry geog);
    
    @Query(value = "SELECT * " +
                   " FROM kelrisks.basias AS si" +
                   " WHERE st_dwithin(si.geog, st_centroid(:geometry), :distance)" +
                   " AND si.raison_sociale LIKE concat('%',:nomProprietaire,'%')", nativeQuery = true)
    List<SiteIndustrielBasias> rechercherParNomProprietaireDansRayonGeometry(Geometry geometry, String nomProprietaire, double distance);
    
    @Query(value = "SELECT * " +
                   " FROM kelrisks.basias AS si" +
                   " WHERE si.id IN (SELECT min(b.id) FROM kelrisks.basias AS b WHERE lower(b.raison_sociale) LIKE concat('%', lower(:query) , '%')" +
                   " GROUP BY b.raison_sociale)", nativeQuery = true)
    List<SiteIndustrielBasias> rechercherRaisonsSociales(String codeINSEE, String query);
    
    @Query(value = "SELECT * " +
                   " FROM kelrisks.basias si" +
                   " LEFT JOIN kelrisks.cadastre AS p ON st_within(si.geog, p.geog)" +
                   " WHERE p.code in :codes", nativeQuery = true)
    List<SiteIndustrielBasias> rechercherSitesSurParcelles(List<String> codes);
}