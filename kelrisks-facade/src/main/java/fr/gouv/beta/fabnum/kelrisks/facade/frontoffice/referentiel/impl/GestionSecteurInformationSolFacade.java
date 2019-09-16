package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.impl;

import fr.gouv.beta.fabnum.commun.facade.AbstractFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SecteurInformationSolDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionSecteurInformationSolFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.refentiel.ISecteurInformationSolMapper;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.IParcelleService;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.ISecteurInformationSolService;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.enums.PrecisionEnum;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo.SecteurInformationSolQO;

import java.util.Arrays;
import java.util.List;

import org.geolatte.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestionSecteurInformationSolFacade extends AbstractFacade implements IGestionSecteurInformationSolFacade {
    
    @Autowired
    ISecteurInformationSolService secteurInformationSolService;
    @Autowired
    IParcelleService              parcelleService;
    @Autowired
    ISecteurInformationSolMapper  secteurInformationSolMapper;
    
    @Override
    public List<SecteurInformationSolDTO> rechercherSitesDansPolygons(List<Geometry> multiPolygon) {
        
        return secteurInformationSolMapper.toDTOs(secteurInformationSolService.rechercherSecteursDansPolygons(multiPolygon));
    }
    
    @Override
    public List<SecteurInformationSolDTO> rechercherSitesDansPolygon(Geometry polygon) {
        
        return secteurInformationSolMapper.toDTOs(secteurInformationSolService.rechercherSecteursDansPolygon(polygon));
    }
    
    @Override
    public List<SecteurInformationSolDTO> rechercherSecteursAvecFaiblePrecisionDeGeolocalisation(String codeINSEE) {
        
        SecteurInformationSolQO secteurInformationSolQO = new SecteurInformationSolQO();
        
        secteurInformationSolQO.setCodeINSEE(codeINSEE);
        List<String> precision = Arrays.asList(PrecisionEnum.COMMUNE.getCode(), PrecisionEnum.LIEU_DIT.getCode(), PrecisionEnum.RUE.getCode());
        secteurInformationSolQO.setPrecisions(precision);
        
        List<SecteurInformationSolDTO> secteurInformationSolDTOS = secteurInformationSolMapper.toDTOs(secteurInformationSolService.rechercherAvecCritere(secteurInformationSolQO));
        
        return secteurInformationSolDTOS;
    }
}
