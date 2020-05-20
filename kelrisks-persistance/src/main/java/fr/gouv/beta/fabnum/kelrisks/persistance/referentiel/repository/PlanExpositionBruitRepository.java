package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.repository;

import fr.gouv.beta.fabnum.commun.persistance.IAbstractRepository;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.PlanExpositionBruit;

import java.util.List;

import org.geolatte.geom.Point;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;

/**
 * Un repository pour PlanExpositionBruit
 * Cette classe est utilisé par Spring data, elle facilite la réalisation des DAO en diminuant le code à écrire.
 * Chaque DAO fait appel au repository associé pour executer ses requêtes JPA (Hibernate)
 */
@Qualifier("planExpositionBruitRepository")
public interface PlanExpositionBruitRepository extends IAbstractRepository<PlanExpositionBruit> {
    
    @Query(value = "SELECT peb.zone " +
                   " FROM kelrisks.peb AS peb" +
                   " WHERE public.st_intersects(peb.geog, :centroid) = TRUE" +
                   " LIMIT 1", nativeQuery = true)
    String rechercherZoneCentroid(Point<?> centroid);
    
    @Query(value = "SELECT * " +
                   " FROM kelrisks.peb AS peb" +
                   " WHERE public.st_dwithin(peb.geog, :centroid, :distance / 100000)", nativeQuery = true)
    List<PlanExpositionBruit> rechercherPlanExpositionBruitDansRayon(Point<?> centroid, double distance);
}
  