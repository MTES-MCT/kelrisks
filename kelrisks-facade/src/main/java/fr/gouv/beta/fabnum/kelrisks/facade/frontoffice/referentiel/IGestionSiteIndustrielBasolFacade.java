package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel;

import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteIndustrielBasolDTO;

import java.util.List;

public interface IGestionSiteIndustrielBasolFacade {
    
    List<SiteIndustrielBasolDTO> rechercherSitesSurParcelle(String codeParcelle);
    
    List<SiteIndustrielBasolDTO> rechercherSiteDansRayonCentroideParcelle(String codeParcelle, Double distance);
}