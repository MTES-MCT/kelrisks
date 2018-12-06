package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.impl;

import fr.gouv.beta.fabnum.commun.facade.AbstractFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteIndustrielBasolDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionSiteIndustrielBasolFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.refentiel.ISiteIndustrielBasolMapper;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.ISiteIndustrielBasolService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestionSiteIndustrielBasolFacade extends AbstractFacade implements IGestionSiteIndustrielBasolFacade {
    
    @Autowired
    ISiteIndustrielBasolService siteIndustrielBasolService;
    @Autowired
    ISiteIndustrielBasolMapper  siteIndustrielBasolMapper;
    
    @Override
    public List<SiteIndustrielBasolDTO> rechercherSitesSurParcelle(String codeParcelle) {
        
        List<SiteIndustrielBasolDTO> siteIndustrielBasolDTOS = siteIndustrielBasolMapper.toDTOs(siteIndustrielBasolService.rechercherSiteSurParcelle(codeParcelle));
        
        return siteIndustrielBasolDTOS;
    }
    
    @Override
    public List<SiteIndustrielBasolDTO> rechercherSiteDansRayonCentroideParcelle(String codeParcelle, Double distance) {
        
        List<SiteIndustrielBasolDTO> siteIndustrielBasolDTOS = siteIndustrielBasolMapper.toDTOs(siteIndustrielBasolService.rechercherSiteDansRayonCentroideParcelle(codeParcelle, distance));
        
        return siteIndustrielBasolDTOS;
    }
}
