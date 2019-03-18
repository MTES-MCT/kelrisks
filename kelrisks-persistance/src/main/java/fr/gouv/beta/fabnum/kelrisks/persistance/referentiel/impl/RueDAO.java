package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.impl;


import fr.gouv.beta.fabnum.commun.persistance.impl.AbstractDAO;
import fr.gouv.beta.fabnum.commun.transverse.exception.technique.TechniqueException;
import fr.gouv.beta.fabnum.commun.transverse.qo.AbstractQO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.IRueDAO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.repository.RueRepository;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.QCommune;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.QRue;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.Rue;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo.CommuneQO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAQueryBase;

/**
 * Classe d'accès DAO à la table rue
 * Date : jeu. 28 juin 2018 - 15:05:15
 *
 * @author DAOACAI Version 2.1.11 du CPII/DOSE
 * @version : 1.0
 */

@Repository("rueDAO")
public class RueDAO extends AbstractDAO<Rue> implements IRueDAO {
    
    /**
     * L'objet issu de QueryDSL pour générer les requêtes "typesafe"
     */
    private QRue rue;
    
    /**
     * Le repository servant à lancer les requêtes JPA.
     */
    private RueRepository rueRepository;
    
    @Autowired
    public RueDAO(@Qualifier("rueRepository") RueRepository repo) {
        
        this.setRepo(repo);
        this.rueRepository = repo;
        this.rue = QRue.rue;
    }
    
    @Override
    protected void ajouterChargementsOptionnels(JPAQueryBase<?, ?> query, AbstractQO[] leCritere) throws TechniqueException {
        
        for (AbstractQO abstractQO : leCritere) {
            
            if (abstractQO instanceof CommuneQO) {
                
                query.leftJoin(QRue.rue.commune(), QCommune.commune).fetchJoin();
            }
        }
    }
    
    @Override
    protected PathBuilder<Rue> getPathBuilder() {
        
        return new PathBuilder<>(Rue.class, "rue");
    }
    
    @Override
    protected EntityPathBase<Rue> getQueryObject() {
        
        return rue;
    }
}
  