package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.impl;

import fr.gouv.beta.fabnum.commun.persistance.impl.AbstractDAO;
import fr.gouv.beta.fabnum.commun.transverse.exception.technique.TechniqueException;
import fr.gouv.beta.fabnum.commun.transverse.qo.AbstractQO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.IShortUrlDAO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.repository.ShortUrlRepository;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.QShortUrl;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.ShortUrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.PathBuilder;
import com.querydsl.jpa.JPAQueryBase;

/**
 * Classe d'accès DAO à la table shortUrl
 * Date : jeu. 28 juin 2018 - 15:05:15
 *
 * @author DAOACAI Version 2.1.11 du CPII/DOSE
 * @version : 1.0
 */

@Repository("shortUrlDAO")
public class ShortUrlDAO extends AbstractDAO<ShortUrl> implements IShortUrlDAO {
    
    /**
     * L'objet issu de QueryDSL pour générer les requêtes "typesafe"
     */
    private QShortUrl shortUrl;
    
    /**
     * Le repository servant à lancer les requêtes JPA.
     */
    private ShortUrlRepository shortUrlRepository;
    
    @Autowired
    public ShortUrlDAO(@Qualifier("shortUrlRepository") ShortUrlRepository repo) {
        
        this.setRepo(repo);
        this.shortUrlRepository = repo;
        this.shortUrl = QShortUrl.shortUrl;
    }
    
    @Override
    protected void ajouterChargementsOptionnels(JPAQueryBase<?, ?> query, AbstractQO[] leCritere) throws TechniqueException {
    
    }
    
    @Override
    protected PathBuilder<ShortUrl> getPathBuilder() {
        
        return new PathBuilder<>(ShortUrl.class, "shortUrl");
    }
    
    @Override
    protected EntityPathBase<ShortUrl> getQueryObject() {
        
        return shortUrl;
    }
}
  