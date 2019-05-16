package fr.gouv.beta.fabnum.kelrisks.presentation.rest;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiKelrisks {
    
    @Value("${kelrisks.app.version}")
    String appVersion;
    @Value("${kelrisks.api.version}")
    String apiVersion;
    
    public ApiKelrisks() {
        // Rien à faire
    }
    
    @GetMapping("/api/apiversion")
    public Response getApiVersion() {
        
        return Response.ok(apiVersion).build();
    }
    
    @GetMapping("/api/appversion")
    public Response getAppVersion() {
        
        return Response.ok(appVersion).build();
    }
}
