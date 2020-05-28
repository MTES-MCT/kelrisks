package fr.gouv.beta.fabnum.kelrisks.presentation.controller;

import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionIGNCartoFacade;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.IGNCartoAssiettePaginatedFeatures;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.IGNCartoGenerateurPaginatedFeatures;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IGNCartoController {
    
    @Autowired
    IGestionIGNCartoFacade ignCartoFacade;
    
    @GetMapping("/ign/assiette/{geom}")
    public IGNCartoAssiettePaginatedFeatures assiette(@PathVariable("geom") String geom) {
        
        return ignCartoFacade.rechercherAssiettesContenantPolygon(geom);
    }
    
    @GetMapping("/ign/generateur/{idgen}/{partition}")
    public IGNCartoGenerateurPaginatedFeatures generateur(@PathVariable("idgen") String idgen, @PathVariable("partition") String partition) {
        
        IGNCartoGenerateurPaginatedFeatures ignCartoGenerateurPaginatedFeatures = ignCartoFacade.rechercherGenerateur(partition);
        
        ignCartoGenerateurPaginatedFeatures.getFeatures().removeIf(generateur -> !generateur.getProperties().getIdgen().equalsIgnoreCase(idgen));
        
        return ignCartoGenerateurPaginatedFeatures;
    }
}
