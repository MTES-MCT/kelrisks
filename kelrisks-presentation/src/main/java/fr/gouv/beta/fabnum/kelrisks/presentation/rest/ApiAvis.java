package fr.gouv.beta.fabnum.kelrisks.presentation.rest;

import fr.gouv.beta.fabnum.kelrisks.facade.avis.AvisDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.avis.IGestionAvisFacade;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class ApiAvis {
    
    @Autowired
    IGestionAvisFacade gestionAvisFacade;
    
    public ApiAvis() {
        // Rien à faire
    }
    
    @RequestMapping("/api/avis/{codeParcelle}/{codePostal}/{rue}/{numero}")
    public Response avis(@ApiParam(required = true, name = "codeParcelle", value = "Code de la parcelle.")
                         @PathVariable("codeParcelle") String codeParcelle,
                         @ApiParam(required = true, name = "codePostal", value = "Code postal de la commune.")
                         @PathVariable("codePostal") String codePostal,
                         @ApiParam(required = true, name = "nomVoie", value = "Nom de la rue.")
                         @PathVariable("nomVoie") String nomVoie,
                         @ApiParam(required = true, name = "numero", value = "Numéro.")
                         @PathVariable("numero") String numero,
                         @ApiParam(required = true, name = "nomProprietaire", value = "Nom du propriétaire / Raison sociale.")
                         @PathVariable("nomProprietaire") String nomProprietaire) {
        
        AvisDTO avisDTO = gestionAvisFacade.rendreAvis(codeParcelle, codePostal, nomVoie, numero, nomProprietaire);
        
        return Response.ok(avisDTO).build();
    }
}
