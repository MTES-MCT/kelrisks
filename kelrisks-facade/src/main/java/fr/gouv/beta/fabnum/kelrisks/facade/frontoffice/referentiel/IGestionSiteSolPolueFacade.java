package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel;

import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteSolPolueDTO;

import java.util.List;

import org.geolatte.geom.Geometry;

public interface IGestionSiteSolPolueFacade {
    
    List<SiteSolPolueDTO> rechercherZoneContenantParcelle(Geometry<?> parcelleGeog);
    
    List<SiteSolPolueDTO> rechercherZoneContenantPolygon(Geometry<?> geometry);
}