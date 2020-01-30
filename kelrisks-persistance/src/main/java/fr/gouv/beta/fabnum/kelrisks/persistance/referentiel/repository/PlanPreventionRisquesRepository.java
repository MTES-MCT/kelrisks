package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.repository;

import fr.gouv.beta.fabnum.commun.persistance.IAbstractRepository;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.PlanPreventionRisques;

import java.util.List;

import org.geolatte.geom.Geometry;
import org.springframework.data.jpa.repository.Query;

/**
 * Un repository pour PlanPreventionRisques
 * Cette classe est utilisé par Spring data, elle facilite la réalisation des DAO en diminuant le code à écrire.
 * Chaque DAO fait appel au repository associé pour executer ses requêtes JPA (Hibernate)
 */
public interface PlanPreventionRisquesRepository extends IAbstractRepository<PlanPreventionRisques> {
    
    @Query(value = "SELECT * " +
                   " FROM kelrisks.ppr AS plan" +
                   " WHERE public.st_intersects(plan.geog, (SELECT public.st_union(:multiPolygons)))", nativeQuery = true)
    List<PlanPreventionRisques> rechercherSitesDansPolygons(List<Geometry<?>> multiPolygons);
    
    @Query(value = "SELECT * " +
                   " FROM kelrisks.ppr AS plan" +
                   " WHERE public.st_intersects(plan.geog, :multiPolygon)", nativeQuery = true)
    List<PlanPreventionRisques> rechercherSitesDansPolygon(Geometry<?> multiPolygon);
}