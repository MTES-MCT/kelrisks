package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel;

import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedAZI;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedCatNat;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedPPR;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedRadon;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedSIS;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedSismique;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedTRI;

public interface IGestionGeorisquesFacade {
    
    GeorisquePaginatedRadon rechercherRadonCommune(String codeINSEE);
    
    GeorisquePaginatedSismique rechercherSismiciteCommune(String codeINSEE);
    
    GeorisquePaginatedCatNat rechercherCatNatCommune(String codeINSEE);
    
    GeorisquePaginatedSIS rechercherSisCoordonnees(double lon, double lat);
    
    GeorisquePaginatedSIS rechercherSisCoordonneesRayon(double lon, double lat, int rayon);
    
    GeorisquePaginatedPPR rechercherPprCoordonnees(double lon, double lat);
    
    GeorisquePaginatedAZI rechercherAZICommune(String codeINSEE);
    
    GeorisquePaginatedTRI rechercherTRICommune(String codeINSEE);
}