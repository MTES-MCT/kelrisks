package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel;

import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.AdresseDTO;

public interface IGestionAdresseFacade {
    
    AdresseDTO rechercherAdresseAvecParcelle(String codeParcelle);
}