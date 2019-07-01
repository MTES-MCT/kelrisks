package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.repository;

import fr.gouv.beta.fabnum.commun.persistance.IAbstractRepository;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.SystemeInformationSols;

import java.util.List;

import org.geolatte.geom.Geometry;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;

/**
 * Un repository pour SystemeInformationSols
 * Cette classe est utilisé par Spring data, elle facilite la réalisation des DAO en diminuant le code à écrire.
 * Chaque DAO fait appel au repository associé pour executer ses requêtes JPA (Hibernate)
 */
@Qualifier("systemeInformationSolsRepository")
public interface SystemeInformationSolsRepository extends IAbstractRepository<SystemeInformationSols> {
    
    @Query("SELECT sis " +
           "FROM SystemeInformationSols sis " +
           "WHERE st_intersects(:geometry, sis.multiPolygon) = TRUE")
    List<SystemeInformationSols> rechercherZoneContenantPolygon(Geometry geometry);
}