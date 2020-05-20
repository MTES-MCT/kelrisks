package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.repository;


import fr.gouv.beta.fabnum.commun.persistance.IAbstractRepository;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.Commune;

import java.util.List;

import org.geolatte.geom.Geometry;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.Query;

/**
 * Un repository pour Commune
 * Cette classe est utilisé par Spring data, elle facilite la réalisation des DAO en diminuant le code à écrire.
 * Chaque DAO fait appel au repository associé pour executer ses requêtes JPA (Hibernate)
 */
@Qualifier("communeRepository")
public interface CommuneRepository extends IAbstractRepository<Commune> {
    
    @Query(value = "SELECT * FROM kelrisks.commune AS com" +
                   " WHERE public.st_touches(com.geog, :geog) AND com.code_insee != :notINSEE", nativeQuery = true)
    List<Commune> rechercherCommunesLimitrophes(Geometry<?> geog, String notINSEE);
}
  