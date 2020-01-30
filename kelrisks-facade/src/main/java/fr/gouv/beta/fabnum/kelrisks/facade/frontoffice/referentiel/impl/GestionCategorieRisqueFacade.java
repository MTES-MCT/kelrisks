package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.impl;

import fr.gouv.beta.fabnum.commun.facade.AbstractFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.CategorieRisqueDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionCategorieRisqueFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.refentiel.ICategorieRisqueMapper;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.ICategorieRisqueService;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo.CategorieRisqueQO;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestionCategorieRisqueFacade extends AbstractFacade implements IGestionCategorieRisqueFacade {
    
    @Autowired
    ICategorieRisqueMapper  categorieRisqueMapper;
    @Autowired
    ICategorieRisqueService categorieRisqueService;
    
    @Override
    public CategorieRisqueDTO rechercherResultatUniqueAvecCritere(CategorieRisqueQO categorieRisqueQO) {
        
        CategorieRisqueDTO categorieRisqueDTO = categorieRisqueMapper.toDTO(categorieRisqueService.rechercherResultatUniqueAvecCritere(categorieRisqueQO));
        
        return categorieRisqueDTO;
    }
    
    @Override
    public List<CategorieRisqueDTO> rechercherAvecCritere(CategorieRisqueQO categorieRisqueQO) {
        
        List<CategorieRisqueDTO> categorieRisqueDTOs = categorieRisqueMapper.toDTOs(categorieRisqueService.rechercherAvecCritere(categorieRisqueQO));
        
        return categorieRisqueDTOs;
    }
}
