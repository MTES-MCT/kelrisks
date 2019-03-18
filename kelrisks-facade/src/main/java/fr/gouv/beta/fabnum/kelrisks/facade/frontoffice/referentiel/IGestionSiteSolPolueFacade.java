package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel;

import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteSolPolueDTO;

public interface IGestionSiteSolPolueFacade {
    
    SiteSolPolueDTO rechercherZoneContenantParcelle(String codeParcelle);
}