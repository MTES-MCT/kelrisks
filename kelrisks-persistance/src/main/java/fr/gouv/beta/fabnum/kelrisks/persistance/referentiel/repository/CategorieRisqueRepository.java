package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.repository;

import fr.gouv.beta.fabnum.commun.persistance.IAbstractRepository;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.CategorieRisque;

import org.springframework.beans.factory.annotation.Qualifier;

/**
 * Un repository pour CategorieRisque
 * Cette classe est utilisé par Spring data, elle facilite la réalisation des DAO en diminuant le code à écrire.
 * Chaque DAO fait appel au repository associé pour executer ses requêtes JPA (Hibernate)
 */
@Qualifier("categorieRisqueRepository")
public interface CategorieRisqueRepository extends IAbstractRepository<CategorieRisque> {
}
  