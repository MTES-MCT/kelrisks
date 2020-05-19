package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel;

import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.CommuneDTO;

import java.util.List;

import org.geolatte.geom.Geometry;

public interface IGestionCommuneFacade {
    
    List<CommuneDTO> rechercherCommunePartielle(String query);
    
    CommuneDTO rechercherCommuneComplete(String codeINSEE);
    
    CommuneDTO rechercherCommuneAvecCodeINSEE(String codeINSEE);
    
    List<CommuneDTO> rechercherCommunesLimitrophes(Geometry<?> geog, String notINSEE);
}