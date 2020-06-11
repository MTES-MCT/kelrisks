package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.impl;

import fr.gouv.beta.fabnum.commun.persistance.impl.AbstractDAO;
import fr.gouv.beta.fabnum.commun.transverse.exception.technique.TechniqueException;
import fr.gouv.beta.fabnum.commun.transverse.qo.AbstractQO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.IArgileDAO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.repository.ArgileRepository;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.Argile;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.QArgile;

import java.util.List;

import org.geolatte.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAQueryBase;

/**
 * Classe d'accès DAO à la table argile
 * Date : jeu. 28 juin 2018 - 15:05:15
 *
 * @author DAOACAI Version 2.1.11 du CPII/DOSE
 * @version : 1.0
 */

@Repository("argileDAO")
public class ArgileDAO extends AbstractDAO<Argile> implements IArgileDAO {
    
    /**
     * L'objet issu de QueryDSL pour générer les requêtes "typesafe"
     */
    private QArgile argile;
    
    /**
     * Le repository servant à lancer les requêtes JPA.
     */
    private ArgileRepository argileRepository;
    
    @Autowired
    public ArgileDAO(@Qualifier("argileRepository") ArgileRepository repo) {
        
        this.setRepo(repo);
        this.argileRepository = repo;
        this.argile = QArgile.argile;
    }
    
    @Override
    public List<Argile> rechercherLentillesDansPolygons(List<Geometry<?>> multiPolygon) {
    
        if (multiPolygon.size() == 1) { return argileRepository.rechercherLentillesDansPolygonEtendu(multiPolygon.get(0), 2000D / 100000D); }
        else { return argileRepository.rechercherLentillesDansPolygonsEtendu(multiPolygon, 50D / 100000D); }
    }
    
    @Override
    public List<Argile> rechercherLentillesDansPolygon(Geometry<?> polygon, double distance) {
        
        return argileRepository.rechercherLentillesDansPolygonEtendu(polygon, distance / 100000D);
    }
    
    @Override
    public Integer rechercherNiveauMaximumArgileDansPolygonEtendu(Geometry<?> polygon, double distance) {
        
        return argileRepository.rechercherNiveauMaximumArgileDansPolygonEtendu(polygon, distance / 100000D);
    }
    
    @Override
    protected void ajouterChargementsOptionnels(JPAQueryBase<?, ?> query, AbstractQO[] leCritere) throws TechniqueException {
    
    }
    
    @Override
    protected PathBuilder<Argile> getPathBuilder() {
        
        return new PathBuilder<>(Argile.class, "argile");
    }
    
    @Override
    protected EntityPathBase<Argile> getQueryObject() {
        
        return argile;
    }
}
  