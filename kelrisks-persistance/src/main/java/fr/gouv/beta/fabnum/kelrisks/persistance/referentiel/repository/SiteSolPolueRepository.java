package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.repository;


import fr.gouv.beta.fabnum.commun.persistance.IAbstractRepository;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.SiteSolPolue;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;

/**
 * Un repository pour SiteSolPolue
 * Cette classe est utilisé par Spring data, elle facilite la réalisation des DAO en diminuant le code à écrire.
 * Chaque DAO fait appel au repository associé pour executer ses requêtes JPA (Hibernate)
 */
@Qualifier("siteSolPolueRepository")
public interface SiteSolPolueRepository extends IAbstractRepository<SiteSolPolue> {
    
    @Query("SELECT ssp " +
           "FROM SiteSolPolue ssp " +
           "WHERE st_intersects((SELECT p.multiPolygon " +
           "                       FROM Parcelle AS p " +
           "                       WHERE p.code = :codeParcelle), " +
           "                     ssp.multiPolygon) = TRUE")
    SiteSolPolue rechercherZoneContenantParcelle(String codeParcelle);
}
  