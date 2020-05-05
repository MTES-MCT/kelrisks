package fr.gouv.beta.fabnum.kelrisks.presentation.controller;

import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionBRGMFacade;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.BRGMPaginatedCanalisation;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.BRGMPaginatedInstallationNuclaire;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BRGMController {
    
    @Autowired
    IGestionBRGMFacade brgmFacade;
    
    @GetMapping("/brgm/canalisation/{insee}")
    public BRGMPaginatedCanalisation canalisationCommune(@PathVariable("insee") String codeINSEE) {
        
        if (!codeINSEE.matches("^\\d{3,5}$")) { return null; }
        
        return brgmFacade.rechercherCanalisationsCommune(codeINSEE);
    }
    
    @GetMapping("/brgm/nucleaire/{lon}/{lat}/{rayon}")
    public BRGMPaginatedInstallationNuclaire canalisationCoordonnees(@PathVariable("lon") Double lon, @PathVariable("lat") Double lat, @PathVariable("rayon") Integer rayon) {
        
        return brgmFacade.rechercherInstallationsNucleairesCoordonnees(String.valueOf(lon), String.valueOf(lat), rayon);
    }
    
    @GetMapping("/brgm/nucleaire/{insee}")
    public BRGMPaginatedInstallationNuclaire nucleaireCommune(@PathVariable("insee") String codeINSEE) {
        
        if (!codeINSEE.matches("^\\d{3,5}$")) { return null; }
        
        return brgmFacade.rechercherInstallationsNucleairesCommune(codeINSEE);
    }
    
    @GetMapping("/brgm/canalisation/{lon}/{lat}/{rayon}")
    public BRGMPaginatedCanalisation nucleaireCoordonnees(@PathVariable("lon") Double lon, @PathVariable("lat") Double lat, @PathVariable("rayon") Integer rayon) {
        
        return brgmFacade.rechercherCanalisationsCoordonnees(String.valueOf(lon), String.valueOf(lat), rayon);
    }
}
