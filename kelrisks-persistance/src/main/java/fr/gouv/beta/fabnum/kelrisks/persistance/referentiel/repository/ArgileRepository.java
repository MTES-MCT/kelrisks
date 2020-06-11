package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.repository;

import fr.gouv.beta.fabnum.commun.persistance.IAbstractRepository;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.Argile;

import java.util.List;

import org.geolatte.geom.Geometry;
import org.springframework.data.jpa.repository.Query;

/**
 * Un repository pour Argile
 * Cette classe est utilisé par Spring data, elle facilite la réalisation des DAO en diminuant le code à écrire.
 * Chaque DAO fait appel au repository associé pour executer ses requêtes JPA (Hibernate)
 */
public interface ArgileRepository extends IAbstractRepository<Argile> {
    
    @Query(value = "SELECT max(niveau_alea) " +
                   " FROM kelrisks.argile AS lentille" +
                   " WHERE public.st_intersects(lentille.geog, public.st_buffer(:multiPolygon, :distance))", nativeQuery = true)
    Integer rechercherNiveauMaximumArgileDansPolygonEtendu(Geometry<?> multiPolygon, double distance);
    
    @Query(value = "SELECT * " +
                   " FROM kelrisks.argile AS lentille" +
                   " WHERE public.st_intersects(lentille.geog, public.st_buffer(:multiPolygon, :distance))", nativeQuery = true)
    List<Argile> rechercherLentillesDansPolygonEtendu(Geometry<?> multiPolygon, double distance);
    
    @Query(value = "SELECT * " +
                   " FROM kelrisks.argile AS lentille" +
                   " WHERE public.st_intersects(lentille.geog, public.st_buffer((SELECT public.st_union(:multiPolygons)), :distance))", nativeQuery = true)
    List<Argile> rechercherLentillesDansPolygonsEtendu(List<Geometry<?>> multiPolygons, double distance);
}