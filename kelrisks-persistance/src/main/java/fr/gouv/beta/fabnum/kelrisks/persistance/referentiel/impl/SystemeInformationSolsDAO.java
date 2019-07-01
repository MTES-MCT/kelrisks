package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.impl;

import fr.gouv.beta.fabnum.commun.persistance.impl.AbstractDAO;
import fr.gouv.beta.fabnum.commun.transverse.exception.technique.TechniqueException;
import fr.gouv.beta.fabnum.commun.transverse.qo.AbstractQO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.ISystemeInformationSolsDAO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.repository.SystemeInformationSolsRepository;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.QSystemeInformationSols;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.SystemeInformationSols;

import java.util.List;

import org.geolatte.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAQueryBase;

/**
 * Classe d'accès DAO à la table systemeInformationSols
 * Date : jeu. 28 juin 2018 - 15:05:15
 *
 * @author DAOACAI Version 2.1.11 du CPII/DOSE
 * @version : 1.0
 */

@Repository("systemeInformationSolsDAO")
public class SystemeInformationSolsDAO extends AbstractDAO<SystemeInformationSols> implements ISystemeInformationSolsDAO {
    
    /**
     * L'objet issu de QueryDSL pour générer les requêtes "typesafe"
     */
    private QSystemeInformationSols systemeInformationSols;
    
    /**
     * Le repository servant à lancer les requêtes JPA.
     */
    private SystemeInformationSolsRepository systemeInformationSolsRepository;
    
    @Autowired
    public SystemeInformationSolsDAO(@Qualifier("systemeInformationSolsRepository") SystemeInformationSolsRepository repo) {
        
        this.setRepo(repo);
        this.systemeInformationSolsRepository = repo;
        this.systemeInformationSols = QSystemeInformationSols.systemeInformationSols;
    }
    
    @Override
    public List<SystemeInformationSols> rechercherZoneContenantPolygon(Geometry geometry) {
        
        return systemeInformationSolsRepository.rechercherZoneContenantPolygon(geometry);
    }
    
    @Override
    protected void ajouterChargementsOptionnels(JPAQueryBase<?, ?> query, AbstractQO[] leCritere) throws TechniqueException {
    
    }
    
    @Override
    protected PathBuilder<SystemeInformationSols> getPathBuilder() {
        
        return new PathBuilder<>(SystemeInformationSols.class, "systemeInformationSols");
    }
    
    @Override
    protected EntityPathBase<SystemeInformationSols> getQueryObject() {
        
        return systemeInformationSols;
    }
}
  