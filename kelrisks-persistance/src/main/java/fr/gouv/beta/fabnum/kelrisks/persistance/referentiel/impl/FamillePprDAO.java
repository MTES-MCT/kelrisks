package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.impl;

import fr.gouv.beta.fabnum.commun.persistance.impl.AbstractDAO;
import fr.gouv.beta.fabnum.commun.transverse.exception.technique.TechniqueException;
import fr.gouv.beta.fabnum.commun.transverse.qo.AbstractQO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.IFamillePprDAO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.repository.FamillePprRepository;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.FamillePPR;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.QFamillePPR;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAQueryBase;

/**
 * Classe d'accès DAO à la table famillePPR
 * Date : jeu. 28 juin 2018 - 15:05:15
 *
 * @author DAOACAI Version 2.1.11 du CPII/DOSE
 * @version : 1.0
 */

@Repository("famillePprDAO")
public class FamillePprDAO extends AbstractDAO<FamillePPR> implements IFamillePprDAO {
    
    /**
     * L'objet issu de QueryDSL pour générer les requêtes "typesafe"
     */
    private QFamillePPR famillePPR;
    
    /**
     * Le repository servant à lancer les requêtes JPA.
     */
    private FamillePprRepository famillePprRepository;
    
    @Autowired
    public FamillePprDAO(@Qualifier("famillePprRepository") FamillePprRepository repo) {
        
        this.setRepo(repo);
        this.famillePprRepository = repo;
        this.famillePPR = QFamillePPR.famillePPR;
    }
    
    @Override
    protected void ajouterChargementsOptionnels(JPAQueryBase<?, ?> query, AbstractQO[] leCritere) throws TechniqueException {
    
    }
    
    @Override
    protected PathBuilder<FamillePPR> getPathBuilder() {
        
        return new PathBuilder<>(FamillePPR.class, "famillePPR");
    }
    
    @Override
    protected EntityPathBase<FamillePPR> getQueryObject() {
        
        return famillePPR;
    }
}
  