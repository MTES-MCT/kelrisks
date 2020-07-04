package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.impl;

import fr.gouv.beta.fabnum.commun.facade.AbstractFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.ArgileDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionArgileFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.referentiel.IArgileMapper;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.IArgileService;

import java.util.List;

import org.geolatte.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestionArgileFacade extends AbstractFacade implements IGestionArgileFacade {
    
    @Autowired
    IArgileService argileService;
    @Autowired
    IArgileMapper  argileMapper;
    
    @Override
    public List<ArgileDTO> rechercherLentillesDansPolygons(List<Geometry<?>> multiPolygon) {
        
        return argileMapper.toDTOs(argileService.rechercherLentillesDansPolygons(multiPolygon));
    }
    
    @Override
    public List<ArgileDTO> rechercherLentillesDansPolygonEtendu(Geometry<?> polygon, double distance) {
        
        return argileMapper.toDTOs(argileService.rechercherLentillesDansPolygon(polygon, distance));
    }
    
    @Override
    public int rechercherNiveauMaximumArgileDansPolygonEtendu(Geometry<?> polygon, double distance) {
        
        return argileService.rechercherNiveauMaximumArgileDansPolygonEtendu(polygon, distance);
    }
}
