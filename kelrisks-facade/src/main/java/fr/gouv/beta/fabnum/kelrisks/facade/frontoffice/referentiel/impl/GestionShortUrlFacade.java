package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.impl;

import fr.gouv.beta.fabnum.commun.facade.AbstractFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.avis.ShortUrlDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionShortUrlFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.refentiel.IShortUrlMapper;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.IShortUrlService;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.ShortUrl;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo.ShortUrlQO;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestionShortUrlFacade extends AbstractFacade implements IGestionShortUrlFacade {
    
    @Autowired
    IShortUrlMapper  shortUrlMapper;
    @Autowired
    IShortUrlService shortUrlService;
    
    @Override
    public ShortUrlDTO rechercherResultatAvecCode(String code) {
        
        ShortUrlQO shortUrlQO = new ShortUrlQO();
        shortUrlQO.setCode(code);
    
        List<ShortUrl> shortUrls = shortUrlService.rechercherAvecCritere(shortUrlQO);
        if (shortUrls.isEmpty()) { return null; }
        ShortUrlDTO shortUrlDTO = shortUrlMapper.toDTO(shortUrls.get(0));
        
        shortUrlDTO.setDateMaj(new Date());
        
        shortUrlService.save(shortUrlMapper.toEntity(shortUrlDTO));
        
        return shortUrlDTO;
    }
    
    @Override
    public void save(ShortUrlDTO shortUrlDTO) {
        
        shortUrlService.save(shortUrlMapper.toEntity(shortUrlDTO));
    }
    
    @Override
    public ShortUrlDTO rechercherResultatAvecUrl(String url) {
        
        ShortUrlQO shortUrlQO = new ShortUrlQO();
        shortUrlQO.setUrl(url);
        
        List<ShortUrlDTO> shortUrlDTOS = shortUrlMapper.toDTOs(shortUrlService.rechercherAvecCritere(shortUrlQO));
        
        if (!shortUrlDTOS.isEmpty()) {
            
            ShortUrlDTO shortUrlDTO = shortUrlDTOS.get(0);
            shortUrlDTO.setDateMaj(new Date());
            shortUrlService.save(shortUrlMapper.toEntity(shortUrlDTO));
            
            return shortUrlDTO;
        }
        
        return null;
    }
}
