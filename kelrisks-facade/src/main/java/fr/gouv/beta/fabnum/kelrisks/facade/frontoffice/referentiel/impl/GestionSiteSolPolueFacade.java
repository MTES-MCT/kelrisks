package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.impl;

import fr.gouv.beta.fabnum.commun.facade.AbstractFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteSolPolueDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionSiteSolPolueFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.referentiel.ISiteSolPolueMapper;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.ISiteSolPolueService;

import java.util.List;

import org.geolatte.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestionSiteSolPolueFacade extends AbstractFacade implements IGestionSiteSolPolueFacade {
    
    @Autowired
    ISiteSolPolueMapper  siteSolPolueMapper;
    @Autowired
    ISiteSolPolueService siteSolPolueService;
    
    @Override
    public List<SiteSolPolueDTO> rechercherZoneContenantParcelle(Geometry<?> parcelleGeog) {
    
        return siteSolPolueMapper.toDTOs(siteSolPolueService.rechercherZoneContenantParcelle(parcelleGeog));
    }
    
    @Override
    public List<SiteSolPolueDTO> rechercherZoneContenantPolygon(Geometry<?> geometry) {
    
        return siteSolPolueMapper.toDTOs(siteSolPolueService.rechercherZoneContenantPolygon(geometry));
    }
}
