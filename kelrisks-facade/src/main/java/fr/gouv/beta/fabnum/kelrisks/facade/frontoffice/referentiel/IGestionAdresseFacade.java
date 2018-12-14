package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel;

import fr.gouv.beta.fabnum.commun.facade.dto.AutocompleteDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.AdresseDTO;

import java.util.List;

public interface IGestionAdresseFacade {
    
    AdresseDTO rechercherAdresseAvecParcelle(String codeParcelle);
    
    List<AutocompleteDTO> rechercherCommunePartielle(String query);
}