package fr.gouv.beta.fabnum.kelrisks.presentation.rest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.core.Response;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Api(tags = {"API"}, description = "API Kelrisks")
public class ApiRest {
    
    public ApiRest() {
        // Rien à faire
    }
    
    @GetMapping("/api/")
    @ApiOperation(value = "Racine de l'API permettant de tester la disponibilité", response = String.class)
    public Response index() {
        
        return Response.ok("L'API REST est démarrée").build();
    }
}
