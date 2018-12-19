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
    
    @Query("SELECT a " +
           "FROM Adresse a " +
           "WHERE st_contains(:geometry, a.point) = TRUE")
    List<Adresse> rechercherAdresseDansGeometry(Geometry geometry);
    
    @Query("SELECT a FROM Adresse a " +
           "WHERE a.id IN ( SELECT min(b.id) " +
           "                FROM Adresse b " +
           "                WHERE b.codePostal LIKE concat('%',:query,'%') OR LOWER(b.nomCommune) LIKE LOWER(concat('%',:query,'%')) GROUP BY b.codeINSEE)")
    List<Adresse> rechercherCommunePartielle(String query);
    
    @Query("SELECT a FROM Adresse a " +
           "WHERE a.id IN ( SELECT min(b.id) " +
           "                FROM Adresse b " +
           "                WHERE b.codeINSEE = :codeINSEE AND LOWER(b.nomVoie) LIKE concat('%',LOWER(:query),'%') GROUP BY b.nomVoie)")
    List<Adresse> rechercherVoiePartielle(String codeINSEE, String query);
}
  