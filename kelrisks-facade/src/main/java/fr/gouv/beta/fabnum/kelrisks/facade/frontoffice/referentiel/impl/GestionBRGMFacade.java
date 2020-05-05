package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.impl;

import fr.gouv.beta.fabnum.commun.facade.AbstractFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionBRGMFacade;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.IBRGMService;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.BRGMPaginatedCanalisation;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.BRGMPaginatedInstallationNuclaire;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestionBRGMFacade extends AbstractFacade implements IGestionBRGMFacade {
    
    @Autowired
    IBRGMService brgmService;
    
    @Override
    public BRGMPaginatedCanalisation rechercherCanalisationsCommune(String codeINSEE) {
        
        return brgmService.rechercherCanalisationsCommune(codeINSEE);
    }
    
    @Override
    public BRGMPaginatedCanalisation rechercherCanalisationsCoordonnees(String lon, String lat, int rayon) {
        
        return brgmService.rechercherCanalisationsCoordonnees(lon, lat, rayon);
    }
    
    @Override
    public BRGMPaginatedInstallationNuclaire rechercherInstallationsNucleairesCommune(String codeINSEE) {
        
        return brgmService.rechercherInstallationsNucleairesCommune(codeINSEE);
    }
    
    @Override
    public BRGMPaginatedInstallationNuclaire rechercherInstallationsNucleairesCoordonnees(String lon, String lat, int rayon) {
        
        return brgmService.rechercherInstallationsNucleairesCoordonnees(lon, lat, rayon);
    }
}
