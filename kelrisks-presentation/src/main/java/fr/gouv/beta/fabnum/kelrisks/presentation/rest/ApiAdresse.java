package fr.gouv.beta.fabnum.kelrisks.presentation.rest;

import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.AdresseDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.ParcelleDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionAdresseFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionParcelleFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = {"API Base Adresse Nationale (BAN)"}, description = "API permettant les recoupements concernant la BAN")
public class ApiAdresse {
    
    @Autowired
    IGestionAdresseFacade  gestionAdresseFacade;
    @Autowired
    IGestionParcelleFacade gestionParcelleFacade;
    
    public ApiAdresse() {
        // Rien à faire
    }
    
    @GetMapping("/api/adresse/cadastre/{nomCommune}/{nomVoie}/{numero}")
    @ApiOperation(value = "Requête retournant une Parcelle à partir d'une Adresse.", response = String.class)
    public Response parcelleFromAdress(@ApiParam(required = true, name = "nomCommune", value = "Nom de la Commune.")
                                       @PathVariable("nomCommune") String nomCommune,
                                       @ApiParam(required = true, name = "nomVoie", value = "Nom de la rue.")
                                       @PathVariable("nomVoie") String nomVoie,
                                       @ApiParam(required = true, name = "numero", value = "Numéro.")
                                       @PathVariable("numero") String numero) {
        
        ParcelleDTO parcelleDTO = gestionParcelleFacade.rechercherParcelleAvecAdresse(nomCommune, nomVoie, numero);
        
        return Response.ok(parcelleDTO).build();
    }
    
    @GetMapping("/api/cadastre/adresse/{codeParcelle}")
    @ApiOperation(value = "Requête retournant une éventuelle adresse à partir d'un code de Parcelle.", response = String.class)
    public Response acdressFromCadastre(@ApiParam(required = true, name = "codeParcelle", value = "Code de la parcelle.")
                                        @PathVariable("codeParcelle") String codeParcelle) {
        
        AdresseDTO adresseDTO = gestionAdresseFacade.rechercherAdresseAvecParcelle(codeParcelle);
        
        return Response.ok(adresseDTO).build();
    }
}
