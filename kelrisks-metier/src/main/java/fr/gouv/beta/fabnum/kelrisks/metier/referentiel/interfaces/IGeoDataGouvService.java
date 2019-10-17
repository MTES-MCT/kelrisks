package fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces;

import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeoDataGouvCommune;

public interface IGeoDataGouvService {
    
    GeoDataGouvCommune rechercherCommune(String latitude, String longitude);
}
