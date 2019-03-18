package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.impl;


import fr.gouv.beta.fabnum.commun.persistance.impl.AbstractDAO;
import fr.gouv.beta.fabnum.commun.transverse.exception.technique.TechniqueException;
import fr.gouv.beta.fabnum.commun.transverse.qo.AbstractQO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.ICommuneDAO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.repository.CommuneRepository;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.Commune;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.QCommune;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAQueryBase;

/**
 * Classe d'accès DAO à la table commune
 * Date : jeu. 28 juin 2018 - 15:05:15
 *
 * @author DAOACAI Version 2.1.11 du CPII/DOSE
 * @version : 1.0
 */

@Repository("communeDAO")
public class CommuneDAO extends AbstractDAO<Commune> implements ICommuneDAO {
    
    /**
     * L'objet issu de QueryDSL pour générer les requêtes "typesafe"
     */
    private QCommune commune;
    
    /**
     * Le repository servant à lancer les requêtes JPA.
     */
    private CommuneRepository communeRepository;
    
    @Autowired
    public CommuneDAO(@Qualifier("communeRepository") CommuneRepository repo) {
        
        this.setRepo(repo);
        this.communeRepository = repo;
        this.commune = QCommune.commune;
    }
    
    @Override
    protected void ajouterChargementsOptionnels(JPAQueryBase<?, ?> query, AbstractQO[] leCritere) throws TechniqueException {
    
    }
    
    @Override
    protected PathBuilder<Commune> getPathBuilder() {
        
        return new PathBuilder<>(Commune.class, "commune");
    }
    
    @Override
    protected EntityPathBase<Commune> getQueryObject() {
        
        return commune;
    }
}
  