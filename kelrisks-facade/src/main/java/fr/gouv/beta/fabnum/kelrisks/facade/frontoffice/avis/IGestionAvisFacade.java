package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.avis;

import fr.gouv.beta.fabnum.kelrisks.facade.avis.AvisDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.CommuneDTO;

public interface IGestionAvisFacade {
    
    AvisDTO rendreAvis(String codeParcelle, CommuneDTO communeDTO, String nomAdresse, String geolocAdresse, String nomProprietaire);
}