package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.impl;

import fr.gouv.beta.fabnum.commun.persistance.impl.AbstractDAO;
import fr.gouv.beta.fabnum.commun.transverse.exception.technique.TechniqueException;
import fr.gouv.beta.fabnum.commun.transverse.qo.AbstractQO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.ICategoriePprDAO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.repository.CategoriePprRepository;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.CategoriePPR;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.QCategoriePPR;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAQueryBase;

/**
 * Classe d'accès DAO à la table categoriePPR
 * Date : jeu. 28 juin 2018 - 15:05:15
 *
 * @author DAOACAI Version 2.1.11 du CPII/DOSE
 * @version : 1.0
 */

@Repository("categoriePprDAO")
public class CategoriePprDAO extends AbstractDAO<CategoriePPR> implements ICategoriePprDAO {
    
    /**
     * L'objet issu de QueryDSL pour générer les requêtes "typesafe"
     */
    private QCategoriePPR categoriePPR;
    
    /**
     * Le repository servant à lancer les requêtes JPA.
     */
    private CategoriePprRepository categoriePprRepository;
    
    @Autowired
    public CategoriePprDAO(@Qualifier("categoriePprRepository") CategoriePprRepository repo) {
        
        this.setRepo(repo);
        this.categoriePprRepository = repo;
        this.categoriePPR = QCategoriePPR.categoriePPR;
    }
    
    @Override
    protected void ajouterChargementsOptionnels(JPAQueryBase<?, ?> query, AbstractQO[] leCritere) throws TechniqueException {
    
    }
    
    @Override
    protected PathBuilder<CategoriePPR> getPathBuilder() {
        
        return new PathBuilder<>(CategoriePPR.class, "categoriePPR");
    }
    
    @Override
    protected EntityPathBase<CategoriePPR> getQueryObject() {
        
        return categoriePPR;
    }
}
  