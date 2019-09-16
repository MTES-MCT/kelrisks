package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.repository;

import fr.gouv.beta.fabnum.commun.persistance.IAbstractRepository;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.SecteurInformationSol;

import java.util.List;

import org.geolatte.geom.Geometry;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;

/**
 * Un repository pour SecteurInformationSol
 * Cette classe est utilisé par Spring data, elle facilite la réalisation des DAO en diminuant le code à écrire.
 * Chaque DAO fait appel au repository associé pour executer ses requêtes JPA (Hibernate)
 */
@Qualifier("secteurInformationSolRepository")
public interface SecteurInformationSolRepository extends IAbstractRepository<SecteurInformationSol> {
    
    @Query(value = "SELECT * " +
                   " FROM kelrisks.sis sis " +
                   " WHERE public.st_intersects(:polygon, sis.geog)", nativeQuery = true)
    List<SecteurInformationSol> rechercherSecteursDansPolygon(Geometry polygon);
    
    @Query(value = "SELECT * " +
                   " FROM kelrisks.sis sis " +
                   " WHERE public.st_intersects((SELECT public.st_union(:polygons)), sis.geog)", nativeQuery = true)
    List<SecteurInformationSol> rechercherSecteursDansPolygons(List<Geometry> polygons);
}