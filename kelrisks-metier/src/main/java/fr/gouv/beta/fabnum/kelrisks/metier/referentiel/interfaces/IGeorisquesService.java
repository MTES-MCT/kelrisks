package fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces;

import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedRadon;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedSismique;

public interface IGeorisquesService {
    
    GeorisquePaginatedRadon rechercherRadonCommune(String codeINSEE);
    
    GeorisquePaginatedSismique rechercherSismiciteCommune(String codeINSEE);
}
