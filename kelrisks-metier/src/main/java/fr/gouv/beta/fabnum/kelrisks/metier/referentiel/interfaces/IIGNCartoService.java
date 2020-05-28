package fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces;

import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.IGNCartoAssiettePaginatedFeatures;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.IGNCartoGenerateurPaginatedFeatures;

public interface IIGNCartoService {
    
    IGNCartoAssiettePaginatedFeatures rechercherAssiettesContenantPolygon(String geom);
    
    IGNCartoGenerateurPaginatedFeatures rechercherGenerateur(String partition);
}
