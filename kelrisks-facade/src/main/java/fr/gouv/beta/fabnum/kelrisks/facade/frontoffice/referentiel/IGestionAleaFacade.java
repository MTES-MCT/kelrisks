package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel;

import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.AleaDTO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo.AleaQO;

import java.util.List;

public interface IGestionAleaFacade {
    
    AleaDTO rechercherResultatUniqueAvecCritere(AleaQO aleaQO);
    
    List<AleaDTO> rechercherAvecCritere(AleaQO aleaQO);
}