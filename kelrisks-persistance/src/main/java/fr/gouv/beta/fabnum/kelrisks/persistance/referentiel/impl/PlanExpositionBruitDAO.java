package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.impl;

import fr.gouv.beta.fabnum.commun.persistance.impl.AbstractDAO;
import fr.gouv.beta.fabnum.commun.transverse.exception.technique.TechniqueException;
import fr.gouv.beta.fabnum.commun.transverse.qo.AbstractQO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.IPlanExpositionBruitDAO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.repository.PlanExpositionBruitRepository;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.PlanExpositionBruit;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.QPlanExpositionBruit;

import java.util.List;

import org.geolatte.geom.Point;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAQueryBase;

/**
 * Classe d'accès DAO à la table planExpositionBruit
 * Date : jeu. 28 juin 2018 - 15:05:15
 *
 * @author DAOACAI Version 2.1.11 du CPII/DOSE
 * @version : 1.0
 */

@Repository("planExpositionBruitDAO")
public class PlanExpositionBruitDAO extends AbstractDAO<PlanExpositionBruit> implements IPlanExpositionBruitDAO {
    
    /**
     * L'objet issu de QueryDSL pour générer les requêtes "typesafe"
     */
    private QPlanExpositionBruit planExpositionBruit;
    
    /**
     * Le repository servant à lancer les requêtes JPA.
     */
    private PlanExpositionBruitRepository planExpositionBruitRepository;
    
    @Autowired
    public PlanExpositionBruitDAO(@Qualifier("planExpositionBruitRepository") PlanExpositionBruitRepository repo) {
        
        this.setRepo(repo);
        this.planExpositionBruitRepository = repo;
        this.planExpositionBruit = QPlanExpositionBruit.planExpositionBruit;
    }
    
    @Override
    public String rechercherZoneCentroid(Point<?> centroid) {
        
        return planExpositionBruitRepository.rechercherZoneCentroid(centroid);
    }
    
    @Override
    public List<PlanExpositionBruit> rechercherPlanExpositionBruitDansRayon(Point<?> centroid, double distance) {
        
        return planExpositionBruitRepository.rechercherPlanExpositionBruitDansRayon(centroid, distance);
    }
    
    @Override
    protected void ajouterChargementsOptionnels(JPAQueryBase<?, ?> query, AbstractQO[] leCritere) throws TechniqueException {
    
    }
    
    @Override
    protected PathBuilder<PlanExpositionBruit> getPathBuilder() {
        
        return new PathBuilder<>(PlanExpositionBruit.class, "planExpositionBruit");
    }
    
    @Override
    protected EntityPathBase<PlanExpositionBruit> getQueryObject() {
        
        return planExpositionBruit;
    }
}
  