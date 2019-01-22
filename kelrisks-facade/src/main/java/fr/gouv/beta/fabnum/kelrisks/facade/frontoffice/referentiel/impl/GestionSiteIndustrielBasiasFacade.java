package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.impl;

import fr.gouv.beta.fabnum.commun.facade.AbstractFacade;
import fr.gouv.beta.fabnum.commun.facade.dto.AutocompleteDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteIndustrielBasiasDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionSiteIndustrielBasiasFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.refentiel.ISiteIndustrielBasiasMapper;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.IParcelleService;
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
    IParcelleService             parcelleService;
    @Autowired
    ISiteIndustrielBasiasMapper  siteIndustrielMapper;
    
    @Override
    public List<SiteIndustrielBasiasDTO> rechercherSitesSurParcelle(String codeParcelle) {
    
        List<SiteIndustrielBasiasDTO> siteIndustrielBasiasDTOs = siteIndustrielMapper.toDTOs(siteIndustrielService.rechercherSiteSurParcelle(codeParcelle));
    
        return siteIndustrielBasiasDTOs;
    }
    
    @Override
    public List<SiteIndustrielBasiasDTO> rechercherSiteDansRayonCentroideParcelle(String codeParcelle, double distance) {
    
        List<SiteIndustrielBasiasDTO> siteIndustrielBasiasDTOs = siteIndustrielMapper.toDTOs(siteIndustrielService.rechercherSiteDansRayonCentroideParcelle(codeParcelle, distance));
    
        return siteIndustrielBasiasDTOs;
    }
    
    @Override
    public List<SiteIndustrielBasiasDTO> rechercherSitesParRaisonSociale(String nomProprietaire) {
    
        SiteIndustrielBasiasQO siteIndustrielBasiasQO = new SiteIndustrielBasiasQO();
        siteIndustrielBasiasQO.setRaisonSociale(nomProprietaire);
    
        List<SiteIndustrielBasiasDTO> siteIndustrielBasiasDTOs = siteIndustrielMapper.toDTOs(siteIndustrielService.rechercherAvecCritere(siteIndustrielBasiasQO));
    
        return siteIndustrielBasiasDTOs;
    }
    
    @Override
    public List<SiteIndustrielBasiasDTO> rechercherSitesDansPolygon(Geometry multiPolygon) {
    
        List<SiteIndustrielBasiasDTO> siteIndustrielBasiasDTOs = siteIndustrielMapper.toDTOs(siteIndustrielService.rechercherSitesDansPolygon(multiPolygon));
    
        return siteIndustrielBasiasDTOs;
    }
    
    @Override
    public List<SiteIndustrielBasiasDTO> rechercherParNomProprietaireDansRayonGeometry(Geometry geometry, String nomProprietaire, double distance) {
        
        List<SiteIndustrielBasiasDTO> siteIndustrielBasiasDTOs = siteIndustrielMapper.toDTOs(siteIndustrielService.rechercherParNomProprietaireDansRayonGeometry(geometry, nomProprietaire, distance));
        
        return siteIndustrielBasiasDTOs;
    }
    
    @Override
    public List<AutocompleteDTO> rechercherRaisonsSociales(String query) {
        
        List<AutocompleteDTO> autocompleteDTOs = siteIndustrielMapper.toAutocompleteDTOs(siteIndustrielService.rechercherRaisonsSociales(query));
        
        return autocompleteDTOs;
    }
    
    @Override
    public List<SiteIndustrielBasiasDTO> rechercherSitesSurParcelles(List<String> codes) {
        
        return siteIndustrielMapper.toDTOs(siteIndustrielService.rechercherSitesSurParcelles(codes));
    }
}
