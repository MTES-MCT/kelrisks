package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.impl;

import fr.gouv.beta.fabnum.commun.facade.AbstractFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionIGNCartoFacade;
import fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces.IIGNCartoService;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.IGNCartoAssiettePaginatedFeatures;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.IGNCartoGenerateurPaginatedFeatures;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestionIGNCartoFacade extends AbstractFacade implements IGestionIGNCartoFacade {
    
    @Autowired
    IIGNCartoService ignCartoService;
    
    @Override
    public IGNCartoAssiettePaginatedFeatures rechercherAssiettesContenantPolygon(String geom) {
        
        return ignCartoService.rechercherAssiettesContenantPolygon(geom);
    }
    
    @Override
    public IGNCartoGenerateurPaginatedFeatures rechercherGenerateur(String partition) {
    
        return ignCartoService.rechercherGenerateur(partition);
    }
}
