package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.impl;

import fr.gouv.beta.fabnum.commun.facade.AbstractFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionGeorisquesFacade;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.IGeorisquesService;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedRadon;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedSIS;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedSismique;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestionGeorisquesFacade extends AbstractFacade implements IGestionGeorisquesFacade {
    
    @Autowired
    IGeorisquesService georisquesService;
    
    public GeorisquePaginatedRadon rechercherRadonCommune(String codeINSEE) {
        
        return georisquesService.rechercherRadonCommune(codeINSEE);
    }
    
    public GeorisquePaginatedSismique rechercherSismiciteCommune(String codeINSEE) {
        
        return georisquesService.rechercherSismiciteCommune(codeINSEE);
    }
    
    @Override
    public GeorisquePaginatedSIS rechercherSisCoordonnees(String lon, String lat) {
        
        return georisquesService.rechercherSisCoordonnees(lon, lat, 1);
    }
    
    @Override
    public GeorisquePaginatedSIS rechercherSisCoordonnees(double lon, double lat) {
        
        return georisquesService.rechercherSisCoordonnees(String.valueOf(lon), String.valueOf(lat), 1);
    }
    
    @Override
    public GeorisquePaginatedSIS rechercherSisCoordonneesRayon(double lon, double lat, int rayon) {
        
        return georisquesService.rechercherSisCoordonnees(String.valueOf(lon), String.valueOf(lat), rayon);
    }
}
