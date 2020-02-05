package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.impl;

import fr.gouv.beta.fabnum.commun.persistance.impl.AbstractDAO;
import fr.gouv.beta.fabnum.commun.transverse.exception.technique.TechniqueException;
import fr.gouv.beta.fabnum.commun.transverse.qo.AbstractQO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.IPlanPreventionRisquesGasparDAO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.repository.PlanPreventionRisquesGasparRepository;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.PlanPreventionRisquesGaspar;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.QAlea;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.QFamilleAlea;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.QFamillePPR;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.QPlanPreventionRisquesGaspar;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAQueryBase;

/**
 * Classe d'accès DAO à la table planPreventionRisquesGaspar
 * Date : jeu. 28 juin 2018 - 15:05:15
 *
 * @author DAOACAI Version 2.1.11 du CPII/DOSE
 * @version : 1.0
 */

@Repository("planPreventionRisquesGasparDAO")
public class PlanPreventionRisquesGasparDAO extends AbstractDAO<PlanPreventionRisquesGaspar> implements IPlanPreventionRisquesGasparDAO {
    
    /**
     * L'objet issu de QueryDSL pour générer les requêtes "typesafe"
     */
    private QPlanPreventionRisquesGaspar planPreventionRisquesGaspar;
    
    /**
     * Le repository servant à lancer les requêtes JPA.
     */
    private PlanPreventionRisquesGasparRepository planPreventionRisquesGasparRepository;
    
    @Autowired
    public PlanPreventionRisquesGasparDAO(@Qualifier("planPreventionRisquesGasparRepository") PlanPreventionRisquesGasparRepository repo) {
        
        this.setRepo(repo);
        this.planPreventionRisquesGasparRepository = repo;
        this.planPreventionRisquesGaspar = QPlanPreventionRisquesGaspar.planPreventionRisquesGaspar;
    }
    
    @Override
    protected void ajouterChargementsOptionnels(JPAQueryBase<?, ?> query, AbstractQO[] leCritere) throws TechniqueException {
    
        query.leftJoin(QPlanPreventionRisquesGaspar.planPreventionRisquesGaspar.alea(), QAlea.alea).fetchJoin();
        query.leftJoin(QAlea.alea.familleAlea(), QFamilleAlea.familleAlea).fetchJoin();
        query.leftJoin(QFamilleAlea.familleAlea.famillePPR(), QFamillePPR.famillePPR).fetchJoin();
    }
    
    @Override
    protected PathBuilder<PlanPreventionRisquesGaspar> getPathBuilder() {
        
        return new PathBuilder<>(PlanPreventionRisquesGaspar.class, "planPreventionRisquesGaspar");
    }
    
    @Override
    protected EntityPathBase<PlanPreventionRisquesGaspar> getQueryObject() {
        
        return planPreventionRisquesGaspar;
    }
}
  