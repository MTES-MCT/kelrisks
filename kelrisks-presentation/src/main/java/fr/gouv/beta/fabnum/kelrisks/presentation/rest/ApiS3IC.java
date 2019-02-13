package fr.gouv.beta.fabnum.kelrisks.presentation.rest;

import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.InstallationClasseeDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionInstallationClasseeFacade;
import io.swagger.annotations.ApiParam;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ApiIgnore
//@Api(tags = {"API Installations Classées (S3IC)"}, description = "API permettant les recoupements concernant les Installations Classées (S3IC)")
public class ApiS3IC extends AbstractBasicApi {
    
    @Autowired
    IGestionInstallationClasseeFacade gestionInstallationClasseeFacade;
    
    public ApiS3IC() {
        // Rien à faire
    }
    
    @GetMapping("/api/s3ic/cadastre/{codeINSEE}/{codeParcelle}")
    //    @ApiOperation(value = "Requête retournant les installations classées liés à une Parcelle.", response = String.class)
    public Response installationFromCadastre(@ApiParam(name = "codeINSEE", value = "Code postal de la commune.")
                                             @PathVariable("codeINSEE") String codeINSEE,
                                             @ApiParam(required = true, name = "codeParcelle", value = "Code de la parcelle.")
                                             @PathVariable("codeParcelle") String codeParcelle) {
        
        List<InstallationClasseeDTO> installationClasseeDTOs = gestionInstallationClasseeFacade.rechercherInstallationsSurParcelle(getParcelleCode(codeINSEE, codeParcelle));
        
        return Response.ok(installationClasseeDTOs).build();
    }
    
    
    @GetMapping("/api/s3ic/cadastre/{codeINSEE}/{codeParcelle}/{distance}")
    //    @ApiOperation(value = "Requête retournant les installations classées dans un certain rayon du centroïde de la Parcelle.", response = String.class)
    public Response installationWithinCadastreRange(@ApiParam(name = "codeINSEE", value = "Code postal de la commune.")
                                                    @PathVariable("codeINSEE") String codeINSEE,
                                                    @ApiParam(required = true, name = "codeParcelle", value = "Code de la parcelle.")
                                                    @PathVariable("codeParcelle") String codeParcelle,
                                                    @ApiParam(required = true, name = "distance", value = "Distance au centroïde de la parcelle (en mètres).", defaultValue = "100")
                                                    @PathVariable("distance") String distance) {
        
        Double rayon = distance.equals("") ? 100D : Double.parseDouble(distance);
        
        List<InstallationClasseeDTO> installationClasseeDTOs = gestionInstallationClasseeFacade.rechercherInstallationsDansRayonCentroideParcelle(getParcelleCode(codeINSEE, codeParcelle), rayon);
        
        return Response.ok(installationClasseeDTOs).build();
    }
    
    @GetMapping("/api/s3ic/adresse/{codeINSEE}")
    //    @ApiOperation(value = "Requête retournant les installations classées associées au centroïde de la commune.", response = String.class)
    public Response installationFromAdresse(@ApiParam(required = true, name = "codeINSEE", value = "Code postal de la commune.")
                                            @PathVariable("codeINSEE") String codeINSEE) {
        
        List<InstallationClasseeDTO> installationClasseeDTO = gestionInstallationClasseeFacade.rechercherInstallationsAuCentroideCommune(codeINSEE);
        
        return Response.ok(installationClasseeDTO).build();
    }
}
