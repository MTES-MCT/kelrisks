package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.impl;

import fr.gouv.beta.fabnum.commun.persistance.impl.AbstractDAO;
import fr.gouv.beta.fabnum.commun.transverse.exception.technique.TechniqueException;
import fr.gouv.beta.fabnum.commun.transverse.qo.AbstractQO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.ISiteIndustrielBasiasDAO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.repository.SiteIndustrielBasiasRepository;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.QSiteIndustrielBasias;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.SiteIndustrielBasias;

import java.util.List;

import org.geolatte.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAQueryBase;

/**
 * Classe d'accès DAO à la table siteIndustrielBasias
 * Date : jeu. 28 juin 2018 - 15:05:15
 *
 * @author DAOACAI Version 2.1.11 du CPII/DOSE
 * @version : 1.0
 */

@Repository("siteIndustrielBasiasDAO")
public class SiteIndustrielBasiasDAO extends AbstractDAO<SiteIndustrielBasias> implements ISiteIndustrielBasiasDAO {
    
    /**
     * L'objet issu de QueryDSL pour générer les requêtes "typesafe"
     */
    private QSiteIndustrielBasias siteIndustrielBasias;
    
    /**
     * Le repository servant à lancer les requêtes JPA.
     */
    private SiteIndustrielBasiasRepository siteIndustrielBasiasRepository;
    
    @Autowired
    public SiteIndustrielBasiasDAO(@Qualifier("siteIndustrielBasiasRepository") SiteIndustrielBasiasRepository repo) {
        
        this.setRepo(repo);
        this.siteIndustrielBasiasRepository = repo;
        this.siteIndustrielBasias = QSiteIndustrielBasias.siteIndustrielBasias;
    }
    
    @Override
    public List<SiteIndustrielBasias> rechercherSiteSurParcelle(String codeParcelle) {
        
        return siteIndustrielBasiasRepository.rechercherSiteSurParcelle(codeParcelle);
    }
    
    @Override
    public List<SiteIndustrielBasias> rechercherSiteDansRayonCentroideParcelle(Geometry<?> geometry, double distance) {
    
        return siteIndustrielBasiasRepository.rechercherSiteDansRayonCentroideParcelle(geometry, distance);
    }
    
    @Override
    public List<SiteIndustrielBasias> rechercherSitesDansPolygons(List<Geometry<?>> multiPolygon) {
        
        if (multiPolygon.size() == 1) { return siteIndustrielBasiasRepository.rechercherSitesDansPolygon(multiPolygon.get(0)); }
        else { return siteIndustrielBasiasRepository.rechercherSitesDansPolygons(multiPolygon); }
    }
    
    @Override
    public List<SiteIndustrielBasias> rechercherSitesDansPolygon(Geometry<?> polygon) {
        
        return siteIndustrielBasiasRepository.rechercherSitesDansPolygon(polygon);
    }
    
    @Override
    public List<SiteIndustrielBasias> rechercherParNomProprietaireDansRayonGeometry(Geometry<?> geometry, String nomProprietaire, double distance) {
        
        return siteIndustrielBasiasRepository.rechercherParNomProprietaireDansRayonGeometry(geometry, nomProprietaire, distance);
    }
    
    @Override
    public List<SiteIndustrielBasias> rechercherRaisonsSociales(String codeINSEE, String query) {
    
        return siteIndustrielBasiasRepository.rechercherRaisonsSociales(codeINSEE, query);
    }
    
    @Override
    public List<SiteIndustrielBasias> rechercherSitesSurParcelles(List<String> codes) {
        
        return siteIndustrielBasiasRepository.rechercherSitesSurParcelles(codes);
    }
    
    @Override
    protected void ajouterChargementsOptionnels(JPAQueryBase<?, ?> query, AbstractQO[] leCritere) throws TechniqueException {
    
    }
    
    @Override
    protected PathBuilder<SiteIndustrielBasias> getPathBuilder() {
        
        return new PathBuilder<>(SiteIndustrielBasias.class, "siteIndustrielBasias");
    }
    
    @Override
    protected EntityPathBase<SiteIndustrielBasias> getQueryObject() {
        
        return siteIndustrielBasias;
    }
}
  