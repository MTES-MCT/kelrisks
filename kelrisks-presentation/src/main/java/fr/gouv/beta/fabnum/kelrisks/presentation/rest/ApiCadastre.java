package fr.gouv.beta.fabnum.kelrisks.presentation.rest;

import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionParcelleFacade;
import io.swagger.annotations.Api;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = {"API Cadastre"}, description = "API permettant des recherches cadastrales")
public class ApiCadastre extends AbstractBasicApi {
    
    @Autowired
    IGestionParcelleFacade gestionParcelleFacade;
    
    public ApiCadastre() {
        // Rien à faire
    }
}
