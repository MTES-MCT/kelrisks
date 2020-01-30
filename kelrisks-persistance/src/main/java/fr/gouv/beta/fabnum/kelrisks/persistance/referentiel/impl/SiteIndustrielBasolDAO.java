package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.impl;

import fr.gouv.beta.fabnum.commun.persistance.impl.AbstractDAO;
import fr.gouv.beta.fabnum.commun.transverse.exception.technique.TechniqueException;
import fr.gouv.beta.fabnum.commun.transverse.qo.AbstractQO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.ISiteIndustrielBasolDAO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.repository.SiteIndustrielBasolRepository;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.QSiteIndustrielBasol;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.SiteIndustrielBasol;

import java.util.List;

import org.geolatte.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAQueryBase;

/**
 * Classe d'accès DAO à la table siteIndustrielBasol
 * Date : jeu. 28 juin 2018 - 15:05:15
 *
 * @author DAOACAI Version 2.1.11 du CPII/DOSE
 * @version : 1.0
 */

@Repository("siteIndustrielBasolDAO")
public class SiteIndustrielBasolDAO extends AbstractDAO<SiteIndustrielBasol> implements ISiteIndustrielBasolDAO {
    
    /**
     * L'objet issu de QueryDSL pour générer les requêtes "typesafe"
     */
    private QSiteIndustrielBasol siteIndustrielBasol;
    
    /**
     * Le repository servant à lancer les requêtes JPA.
     */
    private SiteIndustrielBasolRepository siteIndustrielBasolRepository;
    
    @Autowired
    public SiteIndustrielBasolDAO(@Qualifier("siteIndustrielBasolRepository") SiteIndustrielBasolRepository repo) {
        
        this.setRepo(repo);
        this.siteIndustrielBasolRepository = repo;
        this.siteIndustrielBasol = QSiteIndustrielBasol.siteIndustrielBasol;
    }
    
    @Override
    public List<SiteIndustrielBasol> rechercherSiteSurParcelle(String codeParcelle) {
        
        return siteIndustrielBasolRepository.rechercherSiteSurParcelle(codeParcelle);
    }
    
    @Override
    public List<SiteIndustrielBasol> rechercherSiteDansRayonCentroideParcelle(String codeParcelle, double distance) {
        
        return siteIndustrielBasolRepository.rechercherSiteDansRayonCentroideParcelle(codeParcelle, distance);
    }
    
    @Override
    public List<SiteIndustrielBasol> rechercherSitesDansPolygons(List<Geometry<?>> multiPolygon) {
    
        if (multiPolygon.size() == 1) { return siteIndustrielBasolRepository.rechercherSitesDansPolygon(multiPolygon.get(0)); }
        else { return siteIndustrielBasolRepository.rechercherSitesDansPolygons(multiPolygon); }
    }
    
    @Override
    public List<SiteIndustrielBasol> rechercherSitesSurParcelles(List<String> codes) {
        
        return siteIndustrielBasolRepository.rechercherSitesSurParcelles(codes);
    }
    
    @Override
    public List<SiteIndustrielBasol> rechercherSitesDansPolygon(Geometry<?> polygon) {
        
        return siteIndustrielBasolRepository.rechercherSitesDansPolygon(polygon);
    }
    
    @Override
    protected void ajouterChargementsOptionnels(JPAQueryBase<?, ?> query, AbstractQO[] leCritere) throws TechniqueException {
    
        for (AbstractQO abstractQO : leCritere) {
        
        }
    }
    
    @Override
    protected PathBuilder<SiteIndustrielBasol> getPathBuilder() {
        
        return new PathBuilder<>(SiteIndustrielBasol.class, "siteIndustrielBasol");
    }
    
    @Override
    protected EntityPathBase<SiteIndustrielBasol> getQueryObject() {
        
        return siteIndustrielBasol;
    }
}
  