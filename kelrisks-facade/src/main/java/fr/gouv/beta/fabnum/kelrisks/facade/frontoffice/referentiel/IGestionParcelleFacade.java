package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel;

import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.ParcelleDTO;

public interface IGestionParcelleFacade {
    
    ParcelleDTO rechercherParcelleAvecAdresse(String commune, String rue, String numero);
}