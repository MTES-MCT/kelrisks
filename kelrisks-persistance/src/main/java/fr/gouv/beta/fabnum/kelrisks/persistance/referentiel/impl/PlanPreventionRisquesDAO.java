package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.impl;

import fr.gouv.beta.fabnum.commun.persistance.impl.AbstractDAO;
import fr.gouv.beta.fabnum.commun.transverse.exception.technique.TechniqueException;
import fr.gouv.beta.fabnum.commun.transverse.qo.AbstractQO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.IPlanPreventionRisquesDAO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.repository.PlanPreventionRisquesRepository;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.PlanPreventionRisques;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.QPlanPreventionRisques;

import java.util.List;

import org.geolatte.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAQueryBase;

/**
 * Classe d'accès DAO à la table planPreventionRisques
 * Date : jeu. 28 juin 2018 - 15:05:15
 *
 * @author DAOACAI Version 2.1.11 du CPII/DOSE
 * @version : 1.0
 */

@Repository("planPreventionRisquesDAO")
public class PlanPreventionRisquesDAO extends AbstractDAO<PlanPreventionRisques> implements IPlanPreventionRisquesDAO {
    
    /**
     * L'objet issu de QueryDSL pour générer les requêtes "typesafe"
     */
    private QPlanPreventionRisques planPreventionRisques;
    
    /**
     * Le repository servant à lancer les requêtes JPA.
     */
    private PlanPreventionRisquesRepository planPreventionRisquesRepository;
    
    @Autowired
    public PlanPreventionRisquesDAO(@Qualifier("planPreventionRisquesRepository") PlanPreventionRisquesRepository repo) {
        
        this.setRepo(repo);
        this.planPreventionRisquesRepository = repo;
        this.planPreventionRisques = QPlanPreventionRisques.planPreventionRisques;
    }
    
    @Override
    public List<PlanPreventionRisques> rechercherSitesDansPolygons(List<Geometry<?>> multiPolygon) {
    
        if (multiPolygon.size() == 1) { return planPreventionRisquesRepository.rechercherSitesDansPolygon(multiPolygon.get(0)); }
        else { return planPreventionRisquesRepository.rechercherSitesDansPolygons(multiPolygon); }
    }
    
    @Override
    public List<PlanPreventionRisques> rechercherSitesDansPolygon(Geometry<?> polygon) {
        
        return planPreventionRisquesRepository.rechercherSitesDansPolygon(polygon);
    }
    
    @Override
    protected void ajouterChargementsOptionnels(JPAQueryBase<?, ?> query, AbstractQO[] leCritere) throws TechniqueException {
    
    }
    
    @Override
    protected PathBuilder<PlanPreventionRisques> getPathBuilder() {
        
        return new PathBuilder<>(PlanPreventionRisques.class, "planPreventionRisques");
    }
    
    @Override
    protected EntityPathBase<PlanPreventionRisques> getQueryObject() {
        
        return planPreventionRisques;
    }
}
  