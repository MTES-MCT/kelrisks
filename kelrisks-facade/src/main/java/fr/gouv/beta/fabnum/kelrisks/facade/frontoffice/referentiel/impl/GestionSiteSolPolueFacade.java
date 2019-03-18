package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.impl;

import fr.gouv.beta.fabnum.commun.facade.AbstractFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteSolPolueDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionSiteSolPolueFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.refentiel.ISiteSolPolueMapper;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.ISiteSolPolueService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestionSiteSolPolueFacade extends AbstractFacade implements IGestionSiteSolPolueFacade {
    
    @Autowired
    ISiteSolPolueMapper  siteSolPolueMapper;
    @Autowired
    ISiteSolPolueService siteSolPolueService;
    
    @Override
    public SiteSolPolueDTO rechercherZoneContenantParcelle(String codeParcelle) {
        
        SiteSolPolueDTO siteSolPolueDTO = siteSolPolueMapper.toDTO(siteSolPolueService.rechercherZoneContenantParcelle(codeParcelle));
        
        return siteSolPolueDTO;
    }
}
