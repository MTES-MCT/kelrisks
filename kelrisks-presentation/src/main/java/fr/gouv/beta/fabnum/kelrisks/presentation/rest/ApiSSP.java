package fr.gouv.beta.fabnum.kelrisks.presentation.rest;

import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteSolPolueDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionSiteSolPolueFacade;
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
@Api(tags = {"API Sites et Sols Polués (SSP)"}, description = "API permettant les recoupements concernant les SSP")
public class ApiSSP extends AbstractBasicApi {
    
    @Autowired
    IGestionSiteSolPolueFacade gestionSiteSolPolueFacade;
    
    public ApiSSP() {
        // Rien à faire
    }
    
    @GetMapping("/api/ssp/cadastre/{codeINSEE}/{codeParcelle}")
    @ApiOperation(value = "Requête retournant une éventuelle zone Sites Sols Polués intersectant la Parcelle.", response = String.class)
    public Response acdressFromCadastre(@ApiParam(name = "codeINSEE", value = "Code postal de la commune.")
                                        @PathVariable("codeINSEE") String codeINSEE,
                                        @ApiParam(required = true, name = "codeParcelle", value = "Code de la parcelle.")
                                        @PathVariable("codeParcelle") String codeParcelle) {
    
        List<SiteSolPolueDTO> siteSolPolueDTO = gestionSiteSolPolueFacade.rechercherZoneContenantParcelle(getParcelleCode(codeINSEE, codeParcelle));
    
        // TODO : com.fasterxml.jackson.databind.exc.InvalidDefinitionException: Direct self-reference leading to cycle
        siteSolPolueDTO.forEach(siteSolPolueDTO1 -> siteSolPolueDTO1.setMultiPolygon(null));
        
        return Response.ok(siteSolPolueDTO).build();
    }
}
