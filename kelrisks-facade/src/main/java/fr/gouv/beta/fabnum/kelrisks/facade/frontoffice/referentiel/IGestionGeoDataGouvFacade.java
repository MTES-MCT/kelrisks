package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel;

import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.CommuneDTO;

public interface IGestionGeoDataGouvFacade {
    
    CommuneDTO rechercherCommune(String latitude, String longitude);
}