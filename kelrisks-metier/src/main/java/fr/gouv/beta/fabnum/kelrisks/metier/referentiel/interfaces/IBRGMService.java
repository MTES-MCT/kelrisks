package fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces;

import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.BRGMPaginatedCanalisation;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.BRGMPaginatedInstallationNuclaire;

public interface IBRGMService {
    
    BRGMPaginatedCanalisation rechercherCanalisationsCommune(String codeINSEE);
    
    BRGMPaginatedCanalisation rechercherCanalisationsCoordonnees(String lon, String lat, int rayon);
    
    BRGMPaginatedInstallationNuclaire rechercherInstallationsNucleairesCommune(String codeINSEE);
    
    BRGMPaginatedInstallationNuclaire rechercherInstallationsNucleairesCoordonnees(String lon, String lat, int rayon);
}
