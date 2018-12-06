package fr.gouv.beta.fabnum.kelrisks.presentation.rest;

import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteIndustrielBasolDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionSiteIndustrielBasolFacade;
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
@Api(tags = {"API Basol"}, description = "API permettant les recoupements concernant la table des Sites Industriels Basol")
public class ApiRestBasol {
    
    @Autowired
    IGestionSiteIndustrielBasolFacade gestionSiteIndustrielBasolFacade;
    
    @GetMapping("/api/basol/cadastre/{codeParcelle}")
    @ApiOperation(value = "Requête retournant les sites industiels Basol liés à la Parcelle.", response = String.class)
    public Response basolInCadastre(@ApiParam(required = true, name = "Parcelle", value = "Code de la parcelle.")
                                    @PathVariable("codeParcelle") String codeParcelle) {
        
        List<SiteIndustrielBasolDTO> siteIndustrielBasolDTOS = gestionSiteIndustrielBasolFacade.rechercherSitesSurParcelle(codeParcelle);
        
        return Response.ok(siteIndustrielBasolDTOS).build();
    }
    
    
    @GetMapping("/api/basol/cadastre/{codeParcelle}/{distance}")
    @ApiOperation(value = "Requête retournant les sites industiels Basol dans un certain rayon du centroîde de la Parcelle.", response = String.class)
    public Response basolWithinCadastreRange(@ApiParam(required = true, name = "codeParcelle", value = "Code de la parcelle.")
                                             @PathVariable("codeParcelle") String codeParcelle,
                                             @ApiParam(required = true, name = "distance", value = "Distance au centroïde de la parcelle (en mètres).", defaultValue = "100")
                                             @PathVariable("distance") String distance) {
        
        Double rayon = distance.equals("") ? 100D : Double.parseDouble(distance);
        
        List<SiteIndustrielBasolDTO> siteIndustrielBasolDTOS = gestionSiteIndustrielBasolFacade.rechercherSiteDansRayonCentroideParcelle(codeParcelle, rayon);
        
        return Response.ok(siteIndustrielBasolDTOS).build();
    }
}
