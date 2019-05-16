package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel;

import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteSolPolueDTO;

import java.util.List;

public interface IGestionSiteSolPolueFacade {
    
    List<SiteSolPolueDTO> rechercherZoneContenantParcelle(String codeParcelle);
}