package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.impl;

import fr.gouv.beta.fabnum.commun.facade.AbstractFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteIndustrielBasiasDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionSiteIndustrielBasiasFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.refentiel.ISiteIndustrielBasiasMapper;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.ISiteIndustrielBasiasService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestionSiteIndustrielBasiasFacade extends AbstractFacade implements IGestionSiteIndustrielBasiasFacade {
    
    @Autowired
    ISiteIndustrielBasiasService siteIndustrielService;
    @Autowired
    ISiteIndustrielBasiasMapper  siteIndustrielMapper;
    
    @Override
    public List<SiteIndustrielBasiasDTO> rechercherSitesSurParcelle(String codeParcelle) {
        
        List<SiteIndustrielBasiasDTO> siteIndustrielBasiasDTOS = siteIndustrielMapper.toDTOs(siteIndustrielService.rechercherSiteSurParcelle(codeParcelle));
        
        return siteIndustrielBasiasDTOS;
    }
    
    @Override
    public List<SiteIndustrielBasiasDTO> rechercherSiteDansRayonCentroideParcelle(String codeParcelle, Double distance) {
        
        List<SiteIndustrielBasiasDTO> siteIndustrielBasiasDTOS = siteIndustrielMapper.toDTOs(siteIndustrielService.rechercherSiteDansRayonCentroideParcelle(codeParcelle, distance));
        
        return siteIndustrielBasiasDTOS;
    }
}
