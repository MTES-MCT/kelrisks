package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.impl;


import fr.gouv.beta.fabnum.commun.persistance.impl.AbstractDAO;
import fr.gouv.beta.fabnum.commun.transverse.exception.technique.TechniqueException;
import fr.gouv.beta.fabnum.commun.transverse.qo.AbstractQO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.IAdresseDAO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.repository.AdresseRepository;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.Adresse;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.QAdresse;

import java.util.List;

import org.geolatte.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAQueryBase;

/**
 * Classe d'accès DAO à la table adresse
 * Date : jeu. 28 juin 2018 - 15:05:15
 *
 * @author DAOACAI Version 2.1.11 du CPII/DOSE
 * @version : 1.0
 */

@Repository("adresseDAO")
public class AdresseDAO extends AbstractDAO<Adresse> implements IAdresseDAO {
    
    /**
     * L'objet issu de QueryDSL pour générer les requêtes "typesafe"
     */
    private QAdresse adresse;
    
    /**
     * Le repository servant à lancer les requêtes JPA.
     */
    private AdresseRepository adresseRepository;
    
    @Autowired
    public AdresseDAO(@Qualifier("adresseRepository") AdresseRepository repo) {
        
        this.setRepo(repo);
        this.adresseRepository = repo;
        this.adresse = QAdresse.adresse;
    }
    
    @Override
    public List<Adresse> rechercherAdresseDansGeometry(Geometry geometry) {
        
        return adresseRepository.rechercherAdresseDansGeometry(geometry);
    }
    
    @Override
    public List<Adresse> rechercherCommunePartielle(String query) {
        
        return adresseRepository.rechercherCommunePartielle(query);
    }
    
    @Override
    public List<Adresse> rechercherVoiePartielle(String codePostal, String query) {
        
        return adresseRepository.rechercherVoiePartielle(codePostal, query);
    }
    
    @Override
    protected void ajouterChargementsOptionnels(JPAQueryBase<?, ?> query, AbstractQO[] leCritere) throws TechniqueException {
    
    }
    
    @Override
    protected PathBuilder<Adresse> getPathBuilder() {
        
        return new PathBuilder<>(Adresse.class, "adresse");
    }
    
    @Override
    protected EntityPathBase<Adresse> getQueryObject() {
        
        return adresse;
    }
}
  