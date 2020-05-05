package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.impl;

import fr.gouv.beta.fabnum.commun.facade.AbstractFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.ArgileDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionArgileFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.refentiel.IArgileMapper;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.IArgileService;

import java.util.List;

import org.geolatte.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestionArgileFacade extends AbstractFacade implements IGestionArgileFacade {
    
    @Autowired
    IArgileService ArgileService;
    @Autowired
    IArgileMapper  ArgileMapper;
    
    @Override
    public List<ArgileDTO> rechercherLentillesDansPolygons(List<Geometry<?>> multiPolygon) {
        
        List<ArgileDTO> ArgileDTOs = ArgileMapper.toDTOs(ArgileService.rechercherLentillesDansPolygons(multiPolygon));
        
        return ArgileDTOs;
    }
    
    @Override
    public List<ArgileDTO> rechercherLentillesDansPolygon(Geometry<?> polygon) {
        
        List<ArgileDTO> ArgileDTOs = ArgileMapper.toDTOs(ArgileService.rechercherLentillesDansPolygon(polygon));
        
        return ArgileDTOs;
    }
}
