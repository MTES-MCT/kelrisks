package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.repository;


import fr.gouv.beta.fabnum.commun.persistance.IAbstractRepository;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.Parcelle;

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
    
    @Query(value = "SELECT * FROM kelrisks.cadastre AS p ORDER BY st_distance(p.geog, :point) LIMIT 1", nativeQuery = true)
    Parcelle rechercherParcelleContenantPoint(Geometry point);
}
  