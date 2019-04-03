package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel;

import fr.gouv.beta.fabnum.kelrisks.facade.avis.ShortUrlDTO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public interface IGestionShortUrlFacade {
    
    ShortUrlDTO rechercherResultatAvecCode(String code);
    
    @Transactional(rollbackFor = Exception.class)
    void save(ShortUrlDTO shortUrlDTO);
    
    @Transactional(rollbackFor = Exception.class)
    ShortUrlDTO rechercherResultatAvecUrl(String url);
}