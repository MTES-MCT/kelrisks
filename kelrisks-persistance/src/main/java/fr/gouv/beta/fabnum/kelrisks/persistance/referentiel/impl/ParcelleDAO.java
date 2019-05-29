package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.impl;

import fr.gouv.beta.fabnum.commun.persistance.impl.AbstractDAO;
import fr.gouv.beta.fabnum.commun.transverse.exception.technique.TechniqueException;
import fr.gouv.beta.fabnum.commun.transverse.qo.AbstractQO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.IParcelleDAO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.repository.ParcelleRepository;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.Parcelle;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.QParcelle;

import java.util.List;

import org.geolatte.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAQueryBase;

/**
 * Classe d'accès DAO à la table parcelle
 * Date : jeu. 28 juin 2018 - 15:05:15
 *
 * @author DAOACAI Version 2.1.11 du CPII/DOSE
 * @version : 1.0
 */

@Repository("parcelleDAO")
public class ParcelleDAO extends AbstractDAO<Parcelle> implements IParcelleDAO {
    
    /**
     * L'objet issu de QueryDSL pour générer les requêtes "typesafe"
     */
    private QParcelle parcelle;
    
    /**
     * Le repository servant à lancer les requêtes JPA.
     */
    private ParcelleRepository parcelleRepository;
    
    @Autowired
    public ParcelleDAO(@Qualifier("parcelleRepository") ParcelleRepository repo) {
        
        this.setRepo(repo);
        this.parcelleRepository = repo;
        this.parcelle = QParcelle.parcelle;
    }
    
    @Override
    public Parcelle rechercherParcelleContenantPoint(Geometry point) {
        
        return parcelleRepository.rechercherParcelleContenantPoint(point);
    }
    
    @Override
    public List<Parcelle> rechercherParcellesContigues(Geometry point) {
        
        return parcelleRepository.rechercherParcellesContigues(point);
    }
    
    @Override
    public Parcelle rechercherParcelleAvecCoordonnees(double x, double y) {
        
        return parcelleRepository.rechercherParcelleAvecCoordonnees(x, y);
    }
    
    @Override
    protected void ajouterChargementsOptionnels(JPAQueryBase<?, ?> query, AbstractQO[] leCritere) throws TechniqueException {
    
    }
    
    @Override
    protected PathBuilder<Parcelle> getPathBuilder() {
        
        return new PathBuilder<>(Parcelle.class, "parcelle");
    }
    
    @Override
    protected EntityPathBase<Parcelle> getQueryObject() {
        
        return parcelle;
    }
}
  