package fr.gouv.beta.fabnum.kelrisks.presentation.rest;

import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteIndustrielBasolDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteSolPolueDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionSiteIndustrielBasolFacade;
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
@Api(tags = {"API Basol"}, description = "API permettant les recoupements concernant les Sites Industriels Basol")
public class ApiRestBasol {
    
    @Autowired
    IGestionSiteIndustrielBasolFacade gestionSiteIndustrielBasolFacade;
    @Autowired
    IGestionSiteSolPolueFacade        gestionSiteSolPolueFacade;
    
    @GetMapping("/api/basol/cadastre/{codeParcelle}")
    @ApiOperation(value = "Requête retournant les sites industiels Basol liés à la Parcelle.", response = String.class)
    public Response basolInCadastre(@ApiParam(required = true, name = "codeParcelle", value = "Code de la parcelle.")
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
    
        List<SiteIndustrielBasolDTO> siteIndustrielBasolDTOS = gestionSiteIndustrielBasolFacade.rechercherSiteDansRayonCentroideParcelle(codeParcelle, rayon / 100000D);
    
        return Response.ok(siteIndustrielBasolDTOS).build();
    }
    
    @GetMapping("/api/ssp/basol/cadastre/{codeParcelle}")
    @ApiOperation(value = "Requête retournant les sites industiels Basol liés à la zone Sites Sols Polués intersectant la Parcelle.", response = String.class)
    public Response basolInSSP(@ApiParam(required = true, name = "codeParcelle", value = "Code de la parcelle.")
                               @PathVariable("codeParcelle") String codeParcelle) {
        
        SiteSolPolueDTO              siteSolPolueDTO         = gestionSiteSolPolueFacade.rechercherZoneContenantParcelle(codeParcelle);
        List<SiteIndustrielBasolDTO> siteIndustrielBasolDTOS = gestionSiteIndustrielBasolFacade.rechercherSitesDansPolygon(siteSolPolueDTO.getMultiPolygon());
        
        return Response.ok(siteIndustrielBasolDTOS).build();
    }
}
