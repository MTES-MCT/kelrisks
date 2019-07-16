package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.impl;

import fr.gouv.beta.fabnum.commun.facade.AbstractFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGeorisquesFacade;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.IGeorisquesService;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedRadon;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedSismique;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GeorisquesFacade extends AbstractFacade implements IGeorisquesFacade {
    
    @Autowired
    IGeorisquesService georisquesService;
    
    public GeorisquePaginatedRadon rechercherRadonCommune(String codeINSEE) {
        
        return georisquesService.rechercherRadonCommune(codeINSEE);
    }
    
    public GeorisquePaginatedSismique rechercherSismiciteCommune(String codeINSEE) {
        
        return georisquesService.rechercherSismiciteCommune(codeINSEE);
    }
}
