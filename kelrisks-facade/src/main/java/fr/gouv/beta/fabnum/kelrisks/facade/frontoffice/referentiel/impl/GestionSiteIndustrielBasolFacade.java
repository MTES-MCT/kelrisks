package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.impl;

import fr.gouv.beta.fabnum.commun.facade.AbstractFacade;
import fr.gouv.beta.fabnum.commun.transverse.qo.AbstractQO;
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
    public List<SiteIndustrielBasolDTO> rechercherSitesDansPolygons(List<Geometry> multiPolygon) {
    
        List<SiteIndustrielBasolDTO> siteIndustrielBasolDTOs = siteIndustrielBasolMapper.toDTOs(siteIndustrielBasolService.rechercherSitesDansPolygons(multiPolygon));
    
        return siteIndustrielBasolDTOs;
    }
    
    @Override
    public List<SiteIndustrielBasolDTO> rechercherSitesSurParcelles(List<String> codesParcelle) {
        
        List<SiteIndustrielBasolDTO> siteIndustrielBasolDTOS =
                siteIndustrielBasolMapper.toDTOs(siteIndustrielBasolService.rechercherSitesSurParcelles(codesParcelle));
        
        return siteIndustrielBasolDTOS;
    }
    
    @Override
    public List<SiteIndustrielBasolDTO> rechercherAvecCritere(AbstractQO... criteres) {
        
        return siteIndustrielBasolMapper.toDTOs(siteIndustrielBasolService.rechercherAvecCritere(criteres));
    }
    
    @Override
    public List<SiteIndustrielBasolDTO> rechercherSitesDansPolygon(Geometry polygon) {
        
        List<SiteIndustrielBasolDTO> siteIndustrielBasolDTOs = siteIndustrielBasolMapper.toDTOs(siteIndustrielBasolService.rechercherSitesDansPolygon(polygon));
        
        return siteIndustrielBasolDTOs;
    }
}
