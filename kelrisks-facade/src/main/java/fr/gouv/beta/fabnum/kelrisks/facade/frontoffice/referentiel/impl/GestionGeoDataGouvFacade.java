package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.impl;

import fr.gouv.beta.fabnum.commun.facade.AbstractFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.CommuneDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionGeoDataGouvFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.referentiel.IGeoDataGouvMapper;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.IGeoDataGouvService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestionGeoDataGouvFacade extends AbstractFacade implements IGestionGeoDataGouvFacade {
    
    @Autowired
    IGeoDataGouvService geoDataGouvService;
    @Autowired
    IGeoDataGouvMapper  geoDataGouvMapper;
    
    @Override
    public CommuneDTO rechercherCommune(String latitude, String longitude) {
        
        return geoDataGouvMapper.toDTO(geoDataGouvService.rechercherCommune(latitude, longitude));
    }
}
