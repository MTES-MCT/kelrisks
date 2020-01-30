package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.impl;

import fr.gouv.beta.fabnum.commun.persistance.impl.AbstractDAO;
import fr.gouv.beta.fabnum.commun.transverse.exception.technique.TechniqueException;
import fr.gouv.beta.fabnum.commun.transverse.qo.AbstractQO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.ICategorieRisqueDAO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.repository.CategorieRisqueRepository;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.CategorieRisque;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.QCategorieRisque;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAQueryBase;

/**
 * Classe d'accès DAO à la table categorieRisque
 * Date : jeu. 28 juin 2018 - 15:05:15
 *
 * @author DAOACAI Version 2.1.11 du CPII/DOSE
 * @version : 1.0
 */

@Repository("categorieRisqueDAO")
public class CategorieRisqueDAO extends AbstractDAO<CategorieRisque> implements ICategorieRisqueDAO {
    
    /**
     * L'objet issu de QueryDSL pour générer les requêtes "typesafe"
     */
    private QCategorieRisque categorieRisque;
    
    /**
     * Le repository servant à lancer les requêtes JPA.
     */
    private CategorieRisqueRepository categorieRisqueRepository;
    
    @Autowired
    public CategorieRisqueDAO(@Qualifier("categorieRisqueRepository") CategorieRisqueRepository repo) {
        
        this.setRepo(repo);
        this.categorieRisqueRepository = repo;
        this.categorieRisque = QCategorieRisque.categorieRisque;
    }
    
    @Override
    protected void ajouterChargementsOptionnels(JPAQueryBase<?, ?> query, AbstractQO[] leCritere) throws TechniqueException {
    
    }
    
    @Override
    protected PathBuilder<CategorieRisque> getPathBuilder() {
        
        return new PathBuilder<>(CategorieRisque.class, "categorieRisque");
    }
    
    @Override
    protected EntityPathBase<CategorieRisque> getQueryObject() {
        
        return categorieRisque;
    }
}
  