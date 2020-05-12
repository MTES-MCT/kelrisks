package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.repository;

import fr.gouv.beta.fabnum.commun.persistance.IAbstractRepository;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.Parcelle;

import java.util.List;

import org.geolatte.geom.Geometry;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;

/**
 * Un repository pour Parcelle
 * Cette classe est utilisé par Spring data, elle facilite la réalisation des DAO en diminuant le code à écrire.
 * Chaque DAO fait appel au repository associé pour executer ses requêtes JPA (Hibernate)
 */
@Qualifier("parcelleRepository")
public interface ParcelleRepository extends IAbstractRepository<Parcelle> {
    
    @Query(value = "SELECT * FROM kelrisks.cadastre AS p" +
                   " WHERE public.st_dwithin(:point, p.geog, (100. / 100000.))" +
                   " ORDER BY st_distance(p.geog, :point)" +
                   " LIMIT 1 ", nativeQuery = true)
    Parcelle rechercherClosestParcelleAvecPoint(Geometry<?> point);
    
    @Query(value = "SELECT * FROM kelrisks.cadastre AS p" +
                   " WHERE public.st_touches(p.geog, :geog)", nativeQuery = true)
    List<Parcelle> rechercherParcellesContigues(Geometry<?> geog);
    
    @Query(value = "SELECT * FROM kelrisks.cadastre AS p" +
                   " WHERE public.st_dwithin(public.st_setsrid(public.st_point(:x, :y),4326), p.geog, (100. / 100000.))" +
                   " ORDER BY st_distance(p.geog, public.st_setsrid(public.st_point(:x, :y),4326))" +
                   " LIMIT 1 ", nativeQuery = true)
    Parcelle rechercherClosestParcelleAvecCoordonnees(double x, double y);
    
    @Query(value = "SELECT public.st_asewkt(public.st_buffer(:parcelleGeog, :distance))", nativeQuery = true)
    String rechercherExpendedParcelle(Geometry<?> parcelleGeog, double distance);
    
    @Query(value = "SELECT public.st_asewkt(public.st_union(p.geog)) FROM kelrisks.cadastre AS p" +
                   " WHERE public.st_touches(p.geog, :geog)", nativeQuery = true)
    String rechercherUnionParcellesContigues(Geometry<?> geog);
    
    @Query(value = "SELECT public.st_asewkt(public.st_centroid(:polygon))", nativeQuery = true)
    String rechercherCentroidParcelle(Geometry<?> polygon);
    
    @Query(value = "SELECT public.st_asewkt(public.st_intersects(:polygon, p.geog)) " +
                   " FROM kelrisks.cadastre AS p", nativeQuery = true)
    String rechercherParcellesIntersectionnantSurface(Geometry<?> polygon);
    
    @Query(value = "SELECT * " +
                   " FROM kelrisks.cadastre AS p " +
                   " WHERE public.st_dwithin(public.st_setsrid(public.st_point(:x ,:y), 4326), p.geog, :radius)", nativeQuery = true)
    List<Parcelle> rechercherParcellesDansRayon(double x, double y, double radius);
    
    @Query(value = "SELECT public.st_asewkt(public.st_union(p.geog)) FROM kelrisks.cadastre AS p" +
                   " WHERE p.id IN :ids", nativeQuery = true)
    String rechercherUnionParcelles(List<Long> ids);
}
  