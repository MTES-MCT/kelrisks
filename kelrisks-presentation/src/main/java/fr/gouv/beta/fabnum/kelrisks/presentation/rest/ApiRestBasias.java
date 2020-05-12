package fr.gouv.beta.fabnum.kelrisks.presentation.rest;

import fr.gouv.beta.fabnum.commun.facade.dto.AutocompleteDTO;
import fr.gouv.beta.fabnum.commun.facade.dto.JsonInfoDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.ParcelleDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteIndustrielBasiasDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteSolPolueDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionInstallationClasseeFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionSiteIndustrielBasiasFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionSiteSolPolueFacade;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = {"API Basias"}, description = "API permettant les recoupements concernant les Sites Industriels Basias")
public class ApiRestBasias extends AbstractBasicApi {
    
    private static final String TEXT_PLAIN_UTF8       = MediaType.TEXT_PLAIN + ";charset=UTF-8";
    private static final String APPLICATION_JSON_UTF8 = MediaType.APPLICATION_JSON + ";charset=UTF-8";
    
    @Autowired
    IGestionSiteIndustrielBasiasFacade gestionSiteIndustrielBasiasFacade;
    @Autowired
    IGestionInstallationClasseeFacade  gestionInstallationClasseeFacade;
    @Autowired
    IGestionSiteSolPolueFacade         gestionSiteSolPolueFacade;
    
    public ApiRestBasias() {
        // Rien à faire
    }
    
    @GetMapping("/api/ssp/basias/cadastre/{codeINSEE}/{codeParcelle}")
    @ApiOperation(value = "Requête retournant les sites industiels Basias liés à la zone Sites Sols Polués intersectant la Parcelle.", response = String.class)
    public Response basiasInSSP(@ApiParam(name = "codeINSEE", value = "Code INSEE de la commune.")
                                @PathVariable("codeINSEE") String codeINSEE,
                                @ApiParam(required = true, name = "codeParcelle", value = "Code de la parcelle.")
                                @PathVariable("codeParcelle") String codeParcelle) {
    
        ParcelleDTO parcelleDTO = getParcelleDTO(codeINSEE, codeParcelle);
    
        if (parcelleDTO == null) {
            JsonInfoDTO jsonInfoDTO = new JsonInfoDTO();
            jsonInfoDTO.addError("Aucune parcelle n'a été trouvé avec le code INSEE / code Parcelle fourni");
            return Response.ok(jsonInfoDTO).build();
        }
    
        List<SiteSolPolueDTO> siteSolPolueDTO = gestionSiteSolPolueFacade.rechercherZoneContenantParcelle(parcelleDTO.getMultiPolygon());
        List<SiteIndustrielBasiasDTO> siteIndustrielBasiasDTOs =
                gestionSiteIndustrielBasiasFacade.rechercherSitesDansPolygons(siteSolPolueDTO.stream().map(SiteSolPolueDTO::getMultiPolygon).collect(Collectors.toList()));
    
        return Response.ok(siteIndustrielBasiasDTOs).build();
    }
    
    @GetMapping("/api/basias/cadastre/{codeINSEE}/{codeParcelle}")
    @ApiOperation(value = "Requête retournant les sites industiels Basias liés à la Parcelle.", response = String.class)
    public Response basiasInCadastre(@ApiParam(name = "codeINSEE", value = "Code INSEE de la commune.")
                                     @PathVariable("codeINSEE") String codeINSEE,
                                     @ApiParam(required = true, name = "codeParcelle", value = "Code de la parcelle.")
                                     @PathVariable("codeParcelle") String codeParcelle) {
        
        List<SiteIndustrielBasiasDTO> siteIndustrielBasiasDTOs = gestionSiteIndustrielBasiasFacade.rechercherSitesSurParcelle(getParcelleCode(codeINSEE, codeParcelle));
        
        return Response.ok(siteIndustrielBasiasDTOs).build();
    }
    
    @GetMapping("/api/raison/autocomplete/{codeINSEE}/{query}")
    @ApiOperation(value = "Requête retournant les raisons sociale des sites Basias/S3IC dans une commune.", response = String.class)
    public Response installationsByRaisonSociale(@ApiParam(name = "codeINSEE", value = "Code INSEE de la commune.")
                                                 @PathVariable("codeINSEE") String codeINSEE,
                                                 @ApiParam(required = true, name = "query", value = "Terme partiel.")
                                                 @PathVariable("query") String query) {
        
        List<AutocompleteDTO> autocompleteDTOs = gestionSiteIndustrielBasiasFacade.rechercherRaisonsSociales(codeINSEE, query);
        autocompleteDTOs.addAll(gestionInstallationClasseeFacade.rechercherRaisonsSociales(codeINSEE, query));
        autocompleteDTOs.sort(Comparator.comparing(AutocompleteDTO::getLibelle));
        
        return Response.ok(autocompleteDTOs).build();
    }
    
    @GetMapping("/api/basias/proprietaire/{nomProprietaire}")
    @ApiOperation(value = "Requête retournant les sites industiels Basias liés à la Parcelle.", response = String.class)
    public Response basiasByRaisonSociale(@ApiParam(required = true, name = "nomProprietaire", value = "Nom du propriétaire / Raison sociale.")
                                          @PathVariable("nomProprietaire") String nomProprietaire) {
    
        List<SiteIndustrielBasiasDTO> siteIndustrielBasiasDTOs = gestionSiteIndustrielBasiasFacade.rechercherSitesParRaisonSociale(nomProprietaire);
    
        return Response.ok(siteIndustrielBasiasDTOs).build();
    }
    
    @GetMapping("/api/basias/cadastre/{codeINSEE}/{codeParcelle}/{distance}")
    @ApiOperation(value = "Requête retournant les sites industiels Basias dans un certain rayon du centroîde de la Parcelle.", response = String.class)
    public Response basiasWithinCadastreRange(@ApiParam(name = "codeINSEE", value = "Code INSEE de la commune.")
                                              @PathVariable("codeINSEE") String codeINSEE,
                                              @ApiParam(required = true, name = "codeParcelle", value = "Code de la parcelle.")
                                              @PathVariable("codeParcelle") String codeParcelle,
                                              @ApiParam(required = true, name = "distance", value = "Distance au centroïde de la parcelle (en mètres).", defaultValue = "100")
                                              @PathVariable("distance") String distance) {
        
        Double rayon = distance.equals("") ? 100D : Double.parseDouble(distance);
        
        List<SiteIndustrielBasiasDTO> siteIndustrielBasiasDTOs = gestionSiteIndustrielBasiasFacade.rechercherSiteDansRayonCentroideParcelle(getParcelleCode(codeINSEE, codeParcelle), rayon);
        
        return Response.ok(siteIndustrielBasiasDTOs).build();
    }
}
