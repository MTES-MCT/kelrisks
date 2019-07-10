package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel;

import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SecteurInformationSolDTO;

import java.util.List;

import org.geolatte.geom.Geometry;

public interface IGestionSecteurInformationSolFacade {
    
    List<SecteurInformationSolDTO> rechercherSitesDansPolygons(List<Geometry> multiPolygon);
    
    List<SecteurInformationSolDTO> rechercherSitesDansPolygon(Geometry polygon);
}