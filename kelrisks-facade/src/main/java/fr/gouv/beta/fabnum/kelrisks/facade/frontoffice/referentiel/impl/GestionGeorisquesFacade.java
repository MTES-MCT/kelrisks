package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.impl;

import fr.gouv.beta.fabnum.commun.facade.AbstractFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionGeorisquesFacade;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.IGeorisquesService;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedAZI;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedCatNat;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedPPR;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedRadon;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedSIS;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedSismique;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedTRI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestionGeorisquesFacade extends AbstractFacade implements IGestionGeorisquesFacade {
    
    @Autowired
    IGeorisquesService georisquesService;
    
    public GeorisquePaginatedRadon rechercherRadonCommune(String codeINSEE) {
        
        return georisquesService.rechercherRadonCommune(codeINSEE);
    }
    
    public GeorisquePaginatedAZI rechercherAZICommune(String codeINSEE) {
        
        return georisquesService.rechercherAZICommune(codeINSEE);
    }
    
    public GeorisquePaginatedTRI rechercherTRICommune(String codeINSEE) {
        
        return georisquesService.rechercherTRICommune(codeINSEE);
    }
    
    public GeorisquePaginatedSismique rechercherSismiciteCommune(String codeINSEE) {
        
        return georisquesService.rechercherSismiciteCommune(codeINSEE);
    }
    
    public GeorisquePaginatedCatNat rechercherCatNatCommune(String codeINSEE) {
        
        return georisquesService.rechercherCatNatCommune(codeINSEE);
    }
    
    @Override
    public GeorisquePaginatedSIS rechercherSisCoordonnees(double lon, double lat) {
        
        return georisquesService.rechercherSisCoordonnees(String.valueOf(lon), String.valueOf(lat), 1);
    }
    
    @Override
    public GeorisquePaginatedSIS rechercherSisCoordonneesRayon(double lon, double lat, int rayon) {
        
        return georisquesService.rechercherSisCoordonnees(String.valueOf(lon), String.valueOf(lat), rayon);
    }
    
    @Override
    public GeorisquePaginatedPPR rechercherPprCoordonnees(double lon, double lat) {
    
        return georisquesService.rechercherPprCoordonnees(String.valueOf(lon), String.valueOf(lat), 1);
    }
}
