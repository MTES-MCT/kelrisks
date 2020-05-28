package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel;

import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.IGNCartoAssiettePaginatedFeatures;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.IGNCartoGenerateurPaginatedFeatures;

public interface IGestionIGNCartoFacade {
    
    IGNCartoAssiettePaginatedFeatures rechercherAssiettesContenantPolygon(String geom);
    
    IGNCartoGenerateurPaginatedFeatures rechercherGenerateur(String partition);
}