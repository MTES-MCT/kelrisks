package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel;

import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.CategorieRisqueDTO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo.CategorieRisqueQO;

import java.util.List;

public interface IGestionCategorieRisqueFacade {
    
    CategorieRisqueDTO rechercherResultatUniqueAvecCritere(CategorieRisqueQO categorieRisqueQO);
    
    List<CategorieRisqueDTO> rechercherAvecCritere(CategorieRisqueQO categorieRisqueQO);
}