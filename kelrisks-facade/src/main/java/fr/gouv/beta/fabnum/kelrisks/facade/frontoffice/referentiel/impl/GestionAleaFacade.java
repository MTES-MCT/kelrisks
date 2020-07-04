package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.impl;

import fr.gouv.beta.fabnum.commun.facade.AbstractFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.AleaDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionAleaFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.referentiel.IAleaMapper;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.IAleaService;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo.AleaQO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestionAleaFacade extends AbstractFacade implements IGestionAleaFacade {
    
    @Autowired
    IAleaMapper  aleaMapper;
    @Autowired
    IAleaService aleaService;
    
    @Override
    public AleaDTO rechercherResultatUniqueAvecCritere(AleaQO aleaQO) {
        
        AleaDTO aleaDTO = aleaMapper.toDTO(aleaService.rechercherResultatUniqueAvecCritere(aleaQO));
        
        return aleaDTO;
    }
    
    @Override
    public List<AleaDTO> rechercherAvecCritere(AleaQO aleaQO) {
        
        List<AleaDTO> aleaDTOs = aleaMapper.toDTOs(aleaService.rechercherAvecCritere(aleaQO));
        
        return aleaDTOs;
    }
}
