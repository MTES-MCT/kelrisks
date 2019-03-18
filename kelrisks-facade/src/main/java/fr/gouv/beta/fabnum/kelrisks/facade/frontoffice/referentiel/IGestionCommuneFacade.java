package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel;

import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.CommuneDTO;

import java.util.List;

public interface IGestionCommuneFacade {
    
    List<CommuneDTO> rechercherCommunePartielle(String query);
    
    CommuneDTO rechercherCommuneAvecCodeINSEE(String codeINSEE);
}