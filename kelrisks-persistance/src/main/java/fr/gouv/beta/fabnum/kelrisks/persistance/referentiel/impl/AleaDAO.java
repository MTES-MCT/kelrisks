package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.impl;

import fr.gouv.beta.fabnum.commun.persistance.impl.AbstractDAO;
import fr.gouv.beta.fabnum.commun.transverse.exception.technique.TechniqueException;
import fr.gouv.beta.fabnum.commun.transverse.qo.AbstractQO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.IAleaDAO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.repository.AleaRepository;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.Alea;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.QAlea;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.QFamilleAlea;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.QFamillePPR;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAQueryBase;

/**
 * Classe d'accès DAO à la table alea
 * Date : jeu. 28 juin 2018 - 15:05:15
 *
 * @author DAOACAI Version 2.1.11 du CPII/DOSE
 * @version : 1.0
 */

@Repository("aleaDAO")
public class AleaDAO extends AbstractDAO<Alea> implements IAleaDAO {
    
    /**
     * L'objet issu de QueryDSL pour générer les requêtes "typesafe"
     */
    private QAlea alea;
    
    /**
     * Le repository servant à lancer les requêtes JPA.
     */
    private AleaRepository aleaRepository;
    
    @Autowired
    public AleaDAO(@Qualifier("aleaRepository") AleaRepository repo) {
        
        this.setRepo(repo);
        this.aleaRepository = repo;
        this.alea = QAlea.alea;
    }
    
    @Override
    protected void ajouterChargementsOptionnels(JPAQueryBase<?, ?> query, AbstractQO[] leCritere) throws TechniqueException {
    
        query.leftJoin(QAlea.alea.familleAlea(), QFamilleAlea.familleAlea).fetchJoin();
        query.leftJoin(QFamilleAlea.familleAlea.famillePPR(), QFamillePPR.famillePPR).fetchJoin();
    }
    
    @Override
    protected PathBuilder<Alea> getPathBuilder() {
        
        return new PathBuilder<>(Alea.class, "alea");
    }
    
    @Override
    protected EntityPathBase<Alea> getQueryObject() {
        
        return alea;
    }
}
  