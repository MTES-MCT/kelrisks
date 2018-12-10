package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.impl;

import fr.gouv.beta.fabnum.commun.facade.AbstractFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteIndustrielBasiasDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionSiteIndustrielBasiasFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.refentiel.ISiteIndustrielBasiasMapper;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.ISiteIndustrielBasiasService;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo.SiteIndustrielBasiasQO;

import java.util.List;

import org.geolatte.geom.Geometry;
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
    
    @Override
    public List<SiteIndustrielBasiasDTO> rechercherSitesParRaisonSociale(String nomProprietaire) {
    
        SiteIndustrielBasiasQO siteIndustrielBasiasQO = new SiteIndustrielBasiasQO();
    
        siteIndustrielBasiasQO.setRaisonSociale(nomProprietaire);
    
        List<SiteIndustrielBasiasDTO> siteIndustrielBasiasDTOS = siteIndustrielMapper.toDTOs(siteIndustrielService.rechercherAvecCritere(siteIndustrielBasiasQO));
    
        return siteIndustrielBasiasDTOS;
    }
    
    @Override
    public List<SiteIndustrielBasiasDTO> rechercherSitesDansPolygon(Geometry multiPolygon) {
        
        List<SiteIndustrielBasiasDTO> siteIndustrielBasiasDTOS = siteIndustrielMapper.toDTOs(siteIndustrielService.rechercherSitesDansPolygon(multiPolygon));
        
        return siteIndustrielBasiasDTOS;
    }
}
