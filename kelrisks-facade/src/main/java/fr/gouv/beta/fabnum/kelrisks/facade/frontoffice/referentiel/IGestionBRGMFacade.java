package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel;

import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.BRGMPaginatedCanalisation;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.BRGMPaginatedInstallationNuclaire;

public interface IGestionBRGMFacade {
    
    BRGMPaginatedCanalisation rechercherCanalisationsCommune(String codeINSEE);
    
    BRGMPaginatedCanalisation rechercherCanalisationsCoordonnees(String lon, String lat, int rayon);
    
    BRGMPaginatedInstallationNuclaire rechercherInstallationsNucleairesCommune(String codeINSEE);
    
    BRGMPaginatedInstallationNuclaire rechercherInstallationsNucleairesCoordonnees(String lon, String lat, int rayon);
}