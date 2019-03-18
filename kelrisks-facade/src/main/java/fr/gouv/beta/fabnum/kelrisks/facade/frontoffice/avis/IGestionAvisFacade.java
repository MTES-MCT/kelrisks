package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.avis;

import fr.gouv.beta.fabnum.kelrisks.facade.avis.AvisDTO;

public interface IGestionAvisFacade {
    
    AvisDTO rendreAvis(String codeParcelle, String codePostal, String rue, String numero, String nomProprietaire);
}