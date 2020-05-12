package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.avis;

import fr.gouv.beta.fabnum.kelrisks.facade.avis.AvisDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.CommuneDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.ParcelleDTO;

import java.util.List;

public interface IGestionAvisFacade {
    
    AvisDTO rendreAvis(List<ParcelleDTO> parcelleDTOs, CommuneDTO communeDTO, String nomAdresse);
    
    AvisDTO rendreAvis(String geoJson);
}