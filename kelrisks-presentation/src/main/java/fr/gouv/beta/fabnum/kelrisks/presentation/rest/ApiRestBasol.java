package fr.gouv.beta.fabnum.kelrisks.presentation.rest;

import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteIndustrielBasolDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteSolPolueDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionSiteIndustrielBasolFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionSiteSolPolueFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = {"API Basol"}, description = "API permettant les recoupements concernant les Sites Industriels Basol")
public class ApiRestBasol extends AbstractBasicApi {
    
    @Autowired
    IGestionSiteIndustrielBasolFacade gestionSiteIndustrielBasolFacade;
    @Autowired
    IGestionSiteSolPolueFacade        gestionSiteSolPolueFacade;
    
    @GetMapping("/api/basol/cadastre/{codeINSEE}/{codeParcelle}")
    @ApiOperation(value = "Requête retournant les sites industiels Basol liés à la Parcelle.", response = String.class)
    public Response basolInCadastre(@ApiParam(name = "codeINSEE", value = "Code postal de la commune.")
                                    @PathVariable("codeINSEE") String codeINSEE,
                                    @ApiParam(required = true, name = "codeParcelle", value = "Code de la parcelle.")
                                    @PathVariable("codeParcelle") String codeParcelle) {
        
        List<SiteIndustrielBasolDTO> siteIndustrielBasolDTOs = gestionSiteIndustrielBasolFacade.rechercherSitesSurParcelle(getParcelleCode(codeINSEE, codeParcelle));
        
        return Response.ok(siteIndustrielBasolDTOs).build();
    }
    
    @GetMapping("/api/basol/cadastre/{codeINSEE}/{codeParcelle}/{distance}")
    @ApiOperation(value = "Requête retournant les sites industiels Basol dans un certain rayon du centroîde de la Parcelle.", response = String.class)
    public Response basolWithinCadastreRange(@ApiParam(name = "codeINSEE", value = "Code postal de la commune.")
                                             @PathVariable("codeINSEE") String codeINSEE,
                                             @ApiParam(required = true, name = "codeParcelle", value = "Code de la parcelle.")
                                             @PathVariable("codeParcelle") String codeParcelle,
                                             @ApiParam(required = true, name = "distance", value = "Distance au centroïde de la parcelle (en mètres).", defaultValue = "100")
                                             @PathVariable("distance") String distance) {
        
        Double rayon = distance.equals("") ? 100D : Double.parseDouble(distance);
        
        List<SiteIndustrielBasolDTO> siteIndustrielBasolDTOs = gestionSiteIndustrielBasolFacade.rechercherSiteDansRayonCentroideParcelle(getParcelleCode(codeINSEE, codeParcelle), rayon);
        
        return Response.ok(siteIndustrielBasolDTOs).build();
    }
    
    @GetMapping("/api/ssp/basol/cadastre/{codeINSEE}/{codeParcelle}")
    @ApiOperation(value = "Requête retournant les sites industiels Basol liés à la zone Sites Sols Polués intersectant la Parcelle.", response = String.class)
    public Response basolInSSP(@ApiParam(name = "codeINSEE", value = "Code postal de la commune.")
                               @PathVariable("codeINSEE") String codeINSEE,
                               @ApiParam(required = true, name = "codeParcelle", value = "Code de la parcelle.")
                               @PathVariable("codeParcelle") String codeParcelle) {
    
        List<SiteSolPolueDTO> siteSolPolueDTO = gestionSiteSolPolueFacade.rechercherZoneContenantParcelle(getParcelleCode(codeINSEE, codeParcelle));
        List<SiteIndustrielBasolDTO> siteIndustrielBasolDTOs =
                gestionSiteIndustrielBasolFacade.rechercherSitesDansPolygons(siteSolPolueDTO.stream().map(SiteSolPolueDTO::getMultiPolygon).collect(Collectors.toList()));
        
        return Response.ok(siteIndustrielBasolDTOs).build();
    }
}
