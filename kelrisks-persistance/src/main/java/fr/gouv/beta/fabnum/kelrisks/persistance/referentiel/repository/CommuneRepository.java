package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.repository;


import fr.gouv.beta.fabnum.commun.persistance.IAbstractRepository;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.Commune;

import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Un repository pour Commune
 * Cette classe est utilisé par Spring data, elle facilite la réalisation des DAO en diminuant le code à écrire.
 * Chaque DAO fait appel au repository associé pour executer ses requêtes JPA (Hibernate)
 */
@Qualifier("communeRepository")
public interface CommuneRepository extends IAbstractRepository<Commune> {

}
  