package fr.gouv.beta.fabnum.kelrisks.presentation.rest;

import fr.gouv.beta.fabnum.commun.facade.dto.JsonInfoDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.avis.AvisDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.avis.IGestionAvisFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = {"API Avis Generator"}, description = "API permettant la génération d'avis")
public class ApiAvis extends AbstractBasicApi {
    
    @Autowired
    IGestionAvisFacade gestionAvisFacade;
    
    public ApiAvis() {
        // Rien à faire
    }
    
    @GetMapping("/api/avis")
    @ApiOperation(value = "Requête retournant un avis à partir des paramètres.", response = String.class)
    public Response avis(@ApiParam(required = true, name = "codeParcelle", value = "Code de la parcelle.")
                         @RequestParam("codeParcelle") String codeParcelle,
                         @ApiParam(required = true, name = "codeINSEE", value = "Code postal de la commune.")
                         @RequestParam("codeINSEE") String codeINSEE,
                         @ApiParam(name = "nomVoie", value = "Nom de la voie.")
                         @RequestParam(value = "nomVoie", required = false) String nomVoie,
                         @ApiParam(name = "idBAN", value = "Id BAN (e.g. ADRNIVX_0000002009679805).")
                         @RequestParam(value = "idBAN", required = false) String idBAN,
                         @ApiParam(name = "nomProprietaire", value = "Nom du propriétaire / Raison sociale.")
                         @RequestParam(value = "nomProprietaire", required = false) String nomProprietaire) {
    
        if (codeParcelle != null) {
        
            codeParcelle = getParcelleCode(codeINSEE, codeParcelle);
        
            if (codeParcelle == null) {
                JsonInfoDTO jsonInfoDTO = new JsonInfoDTO();
                jsonInfoDTO.addError("Le code parcelle n'a pas été trouvé.");
                return Response.ok(jsonInfoDTO).build();
            }
        }
    
        AvisDTO avisDTO = gestionAvisFacade.rendreAvis(codeParcelle, codeINSEE, nomVoie, idBAN, nomProprietaire);
        
        return Response.ok(avisDTO).build();
    }
}