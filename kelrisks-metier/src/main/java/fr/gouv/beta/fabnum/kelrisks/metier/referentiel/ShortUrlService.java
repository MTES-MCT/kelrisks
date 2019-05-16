package fr.gouv.beta.fabnum.kelrisks.metier.referentiel;

import fr.gouv.beta.fabnum.commun.metier.impl.AbstractCRUDService;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.IShortUrlService;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.IShortUrlDAO;
import fr.gouv.beta.fabnum.kelrisks.persistance.referentiel.impl.ShortUrlDAO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.ShortUrl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

/**
 * Service métier de gestion de l'entité ShortUrl
 */

@Service("shortUrlService")
public class ShortUrlService extends AbstractCRUDService<ShortUrl> implements IShortUrlService {
    
    private static final long serialVersionUID = 1L;
    
    /*
     * Le dao spécifique à ce service
     */
    private IShortUrlDAO dao;
    
    @Autowired
    public ShortUrlService(@Qualifier("shortUrlDAO") final ShortUrlDAO fdao) {
        
        this.setFdao(fdao);
        dao = fdao;
    }
}
  