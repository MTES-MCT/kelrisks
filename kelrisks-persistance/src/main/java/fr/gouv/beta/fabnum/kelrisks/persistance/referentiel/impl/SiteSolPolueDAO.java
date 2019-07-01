package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.impl;

import fr.gouv.beta.fabnum.commun.persistance.impl.AbstractDAO;
import fr.gouv.beta.fabnum.commun.transverse.exception.technique.TechniqueException;
import fr.gouv.beta.fabnum.commun.transverse.qo.AbstractQO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.ISiteSolPolueDAO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.repository.SiteSolPolueRepository;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.QSiteSolPolue;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.SiteSolPolue;

import java.util.List;

import org.geolatte.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAQueryBase;

/**
 * Classe d'accès DAO à la table siteSolPolue
 * Date : jeu. 28 juin 2018 - 15:05:15
 *
 * @author DAOACAI Version 2.1.11 du CPII/DOSE
 * @version : 1.0
 */

@Repository("siteSolPolueDAO")
public class SiteSolPolueDAO extends AbstractDAO<SiteSolPolue> implements ISiteSolPolueDAO {
    
    /**
     * L'objet issu de QueryDSL pour générer les requêtes "typesafe"
     */
    private QSiteSolPolue siteSolPolue;
    
    /**
     * Le repository servant à lancer les requêtes JPA.
     */
    private SiteSolPolueRepository siteSolPolueRepository;
    
    @Autowired
    public SiteSolPolueDAO(@Qualifier("siteSolPolueRepository") SiteSolPolueRepository repo) {
        
        this.setRepo(repo);
        this.siteSolPolueRepository = repo;
        this.siteSolPolue = QSiteSolPolue.siteSolPolue;
    }
    
    @Override
    public List<SiteSolPolue> rechercherZoneContenantPolygon(Geometry geometry) {
        
        return siteSolPolueRepository.rechercherZoneContenantPolygon(geometry);
    }
    
    @Override
    public List<SiteSolPolue> rechercherZoneContenantParcelle(String codeParcelle) {
        
        return siteSolPolueRepository.rechercherZoneContenantParcelle(codeParcelle);
    }
    
    @Override
    protected void ajouterChargementsOptionnels(JPAQueryBase<?, ?> query, AbstractQO[] leCritere) throws TechniqueException {
    
    }
    
    @Override
    protected PathBuilder<SiteSolPolue> getPathBuilder() {
        
        return new PathBuilder<>(SiteSolPolue.class, "siteSolPolue");
    }
    
    @Override
    protected EntityPathBase<SiteSolPolue> getQueryObject() {
        
        return siteSolPolue;
    }
}
  