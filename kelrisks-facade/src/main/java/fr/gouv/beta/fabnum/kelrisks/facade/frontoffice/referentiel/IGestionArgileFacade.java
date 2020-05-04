package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel;

import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.ArgileDTO;

import java.util.List;

import org.geolatte.geom.Geometry;

public interface IGestionArgileFacade {
    
    List<ArgileDTO> rechercherLentillesDansPolygons(List<Geometry<?>> multiPolygons);
    
    List<ArgileDTO> rechercherLentillesDansPolygon(Geometry<?> multiPolygons);
}