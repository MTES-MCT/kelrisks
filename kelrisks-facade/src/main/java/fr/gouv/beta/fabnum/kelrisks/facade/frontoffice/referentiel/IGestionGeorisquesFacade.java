package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel;

import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedRadon;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedSIS;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedSismique;

public interface IGestionGeorisquesFacade {
    
    GeorisquePaginatedRadon rechercherRadonCommune(String codeINSEE);
    
    GeorisquePaginatedSismique rechercherSismiciteCommune(String codeINSEE);
    
    GeorisquePaginatedSIS rechercherSisCoordonnees(String lon, String lat);
    
    GeorisquePaginatedSIS rechercherSisCoordonnees(double lon, double lat);
    
    GeorisquePaginatedSIS rechercherSisCoordonneesRayon(double lon, double lat, int rayon);
}