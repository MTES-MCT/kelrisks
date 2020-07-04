package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.impl;

import fr.gouv.beta.fabnum.commun.facade.AbstractFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.CommuneDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionAdresseDataGouvFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.referentiel.IAdresseDataGouvMapper;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.IAdresseDataGouvService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestionAdresseDataGouvFacade extends AbstractFacade implements IGestionAdresseDataGouvFacade {
    
    @Autowired
    IAdresseDataGouvService adresseDataGouvService;
    @Autowired
    IAdresseDataGouvMapper  adresseDataGouvMapper;
    
    @Override
    public List<CommuneDTO> rechercherCommune(String codeINSEE) {
        
        return adresseDataGouvMapper.toDTOs(adresseDataGouvService.rechercherCommune(codeINSEE));
    }
}
