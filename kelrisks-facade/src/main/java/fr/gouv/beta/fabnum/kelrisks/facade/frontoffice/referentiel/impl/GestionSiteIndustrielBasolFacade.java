package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.impl;

import fr.gouv.beta.fabnum.commun.facade.AbstractFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteIndustrielBasolDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionSiteIndustrielBasolFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.refentiel.ISiteIndustrielBasolMapper;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.IParcelleService;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.ISiteIndustrielBasolService;

import java.util.List;

import org.geolatte.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestionSiteIndustrielBasolFacade extends AbstractFacade implements IGestionSiteIndustrielBasolFacade {
    
    @Autowired
    ISiteIndustrielBasolService siteIndustrielBasolService;
    @Autowired
    IParcelleService            parcelleService;
    @Autowired
    ISiteIndustrielBasolMapper  siteIndustrielBasolMapper;
    
    @Override
    public List<SiteIndustrielBasolDTO> rechercherSitesSurParcelle(String codeParcelle) {
    
        List<SiteIndustrielBasolDTO> siteIndustrielBasolDTOs = siteIndustrielBasolMapper.toDTOs(siteIndustrielBasolService.rechercherSiteSurParcelle(codeParcelle));
    
        return siteIndustrielBasolDTOs;
    }
    
    @Override
    public List<SiteIndustrielBasolDTO> rechercherSiteDansRayonCentroideParcelle(String codeParcelle, double distance) {
    
        List<SiteIndustrielBasolDTO> siteIndustrielBasolDTOs = siteIndustrielBasolMapper.toDTOs(siteIndustrielBasolService.rechercherSiteDansRayonCentroideParcelle(codeParcelle, distance));
    
        return siteIndustrielBasolDTOs;
    }
    
    @Override
    public List<SiteIndustrielBasolDTO> rechercherSitesDansPolygon(List<Geometry> multiPolygon) {
    
        List<SiteIndustrielBasolDTO> siteIndustrielBasolDTOs = siteIndustrielBasolMapper.toDTOs(siteIndustrielBasolService.rechercherSitesDansPolygon(multiPolygon));
    
        return siteIndustrielBasolDTOs;
    }
    
    @Override
    public List<SiteIndustrielBasolDTO> rechercherSitesSurParcelles(List<String> codesParcelle) {
        
        List<SiteIndustrielBasolDTO> siteIndustrielBasolDTOS =
                siteIndustrielBasolMapper.toDTOs(siteIndustrielBasolService.rechercherSitesSurParcelles(codesParcelle));
        
        return siteIndustrielBasolDTOS;
    }
}
