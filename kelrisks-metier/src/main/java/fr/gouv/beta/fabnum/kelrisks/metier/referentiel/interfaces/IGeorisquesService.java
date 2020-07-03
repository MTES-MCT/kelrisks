package fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces;

import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedAZI;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedCatNat;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedPPR;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedRadon;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedSIS;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedSismique;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedTRI;

public interface IGeorisquesService {
    
    GeorisquePaginatedRadon rechercherRadonCommune(String codeINSEE);
    
    GeorisquePaginatedAZI rechercherAZICommune(String codeINSEE);
    
    GeorisquePaginatedTRI rechercherTRICommune(String codeINSEE);
    
    public GeorisquePaginatedCatNat rechercherCatNatCommune(String codeINSEE);
    
    GeorisquePaginatedSismique rechercherSismiciteCommune(String codeINSEE);
    
    GeorisquePaginatedSIS rechercherSisCoordonnees(String lon, String lat, int rayon);
    
    GeorisquePaginatedPPR rechercherPprCoordonnees(String lon, String lat, int rayon);
}
