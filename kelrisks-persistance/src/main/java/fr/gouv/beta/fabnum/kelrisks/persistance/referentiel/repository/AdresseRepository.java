package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.repository;


import fr.gouv.beta.fabnum.commun.persistance.IAbstractRepository;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.Adresse;

import java.util.List;

import org.geolatte.geom.Geometry;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;

/**
 * Un repository pour Adresse
 * Cette classe est utilisé par Spring data, elle facilite la réalisation des DAO en diminuant le code à écrire.
 * Chaque DAO fait appel au repository associé pour executer ses requêtes JPA (Hibernate)
 */
@Qualifier("adresseRepository")
public interface AdresseRepository extends IAbstractRepository<Adresse> {
    
    @Query(value = "SELECT * " +
                   " FROM kelrisks.adresse a" +
                   " WHERE st_contains(:geometry, a.point) = TRUE", nativeQuery = true)
    List<Adresse> rechercherAdresseDansGeometry(Geometry geometry);
}
  