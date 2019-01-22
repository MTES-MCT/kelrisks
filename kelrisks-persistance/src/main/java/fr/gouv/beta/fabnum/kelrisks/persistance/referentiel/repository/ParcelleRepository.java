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
    Parcelle rechercherParcelleContenantPoint(Geometry point);
    
    @Query(value = "SELECT * FROM kelrisks.cadastre AS p" +
                   " WHERE public.st_touches(p.geog, :geog)", nativeQuery = true)
    List<Parcelle> rechercherParcellesContigues(Geometry geog);
}
  