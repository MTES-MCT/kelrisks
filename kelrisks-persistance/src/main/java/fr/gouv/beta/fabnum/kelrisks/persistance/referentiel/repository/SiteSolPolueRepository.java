package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.repository;

import fr.gouv.beta.fabnum.commun.persistance.IAbstractRepository;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.SiteSolPolue;

import java.util.List;

import org.geolatte.geom.Geometry;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;

/**
 * Un repository pour SiteSolPolue
 * Cette classe est utilisé par Spring data, elle facilite la réalisation des DAO en diminuant le code à écrire.
 * Chaque DAO fait appel au repository associé pour executer ses requêtes JPA (Hibernate)
 */
@Qualifier("siteSolPolueRepository")
public interface SiteSolPolueRepository extends IAbstractRepository<SiteSolPolue> {
    
    @Query(value = "SELECT * " +
                   " FROM kelrisks.ssp ssp, " +
                   " (SELECT * FROM kelrisks.cadastre AS p WHERE p.code = :codeParcelle) parcelle" +
                   " WHERE public.st_intersects(parcelle.geog,  ssp.geog)", nativeQuery = true)
    List<SiteSolPolue> rechercherZoneContenantParcelle(String codeParcelle);
    
    @Query(value = "SELECT * " +
                   " FROM kelrisks.ssp ssp " +
                   " WHERE public.st_intersects(:geometry, ssp.geog)", nativeQuery = true)
    List<SiteSolPolue> rechercherZoneContenantPolygon(Geometry<?> geometry);
}
  