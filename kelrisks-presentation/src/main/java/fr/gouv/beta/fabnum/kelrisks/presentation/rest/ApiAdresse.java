package fr.gouv.beta.fabnum.kelrisks.presentation.rest;

import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.CommuneDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionCommuneFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionParcelleFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = {"API Base Adresse Nationale (BAN)"}, description = "API permettant les recoupements concernant la BAN")
public class ApiAdresse extends AbstractBasicApi {
    
    @Autowired
    IGestionCommuneFacade  gestionCommuneFacade;
    @Autowired
    IGestionParcelleFacade gestionParcelleFacade;
    
    public ApiAdresse() {
        // Rien à faire
    }
    
    @GetMapping("/api/adresse/commune/autocomplete/{query}")
    @ApiOperation(value = "Requête retournant des communes à partir d'une recherche partielle.", response = String.class)
    public Response communePartielle(@ApiParam(required = true, name = "query", value = "Terme partiel.")
                                     @PathVariable("query") String query) {
    
        List<CommuneDTO> autocompleteDTOs = gestionCommuneFacade.rechercherCommunePartielle(query);
        
        return Response.ok(autocompleteDTOs).build();
    }
}
