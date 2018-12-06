package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel;

import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteIndustrielBasiasDTO;

import java.util.List;

public interface IGestionSiteIndustrielBasiasFacade {
    
    List<SiteIndustrielBasiasDTO> rechercherSitesSurParcelle(String codeParcelle);
    
    List<SiteIndustrielBasiasDTO> rechercherSiteDansRayonCentroideParcelle(String codeParcelle, Double distance);
}