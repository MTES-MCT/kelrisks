package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.impl;

import fr.gouv.beta.fabnum.commun.persistance.impl.AbstractDAO;
import fr.gouv.beta.fabnum.commun.transverse.exception.technique.TechniqueException;
import fr.gouv.beta.fabnum.commun.transverse.qo.AbstractQO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.ISecteurInformationSolDAO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.repository.SecteurInformationSolRepository;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.QSecteurInformationSol;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.SecteurInformationSol;

import java.util.List;

import org.geolatte.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAQueryBase;

/**
 * Classe d'accès DAO à la table SecteurInformationSol
 * Date : jeu. 28 juin 2018 - 15:05:15
 *
 * @author DAOACAI Version 2.1.11 du CPII/DOSE
 * @version : 1.0
 */

@Repository("secteurInformationSolDAO")
public class SecteurInformationSolDAO extends AbstractDAO<SecteurInformationSol> implements ISecteurInformationSolDAO {
    
    /**
     * L'objet issu de QueryDSL pour générer les requêtes "typesafe"
     */
    private QSecteurInformationSol secteurInformationSol;
    
    /**
     * Le repository servant à lancer les requêtes JPA.
     */
    private SecteurInformationSolRepository secteurInformationSolRepository;
    
    @Autowired
    public SecteurInformationSolDAO(@Qualifier("secteurInformationSolRepository") SecteurInformationSolRepository repo) {
        
        this.setRepo(repo);
        this.secteurInformationSolRepository = repo;
        this.secteurInformationSol = QSecteurInformationSol.secteurInformationSol;
    }
    
    @Override
    public List<SecteurInformationSol> rechercherSecteursDansPolygon(Geometry<?> polygon) {
    
        return secteurInformationSolRepository.rechercherSecteursDansPolygon(polygon);
    }
    
    @Override
    public List<SecteurInformationSol> rechercherSecteursDansPolygons(List<Geometry<?>> polygons) {
        
        return secteurInformationSolRepository.rechercherSecteursDansPolygons(polygons);
    }
    
    @Override
    protected void ajouterChargementsOptionnels(JPAQueryBase<?, ?> query, AbstractQO[] leCritere) throws TechniqueException {
    
    }
    
    @Override
    protected PathBuilder<SecteurInformationSol> getPathBuilder() {
    
        return new PathBuilder<>(SecteurInformationSol.class, "SecteurInformationSol");
    }
    
    @Override
    protected EntityPathBase<SecteurInformationSol> getQueryObject() {
    
        return secteurInformationSol;
    }
}
  