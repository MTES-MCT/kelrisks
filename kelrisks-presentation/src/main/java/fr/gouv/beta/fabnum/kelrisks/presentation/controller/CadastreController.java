package fr.gouv.beta.fabnum.kelrisks.presentation.controller;

import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.ParcelleDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionParcelleFacade;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.core.Response;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CadastreController {
    
    @Autowired
    IGestionParcelleFacade gestionParcelleFacade;
    
    @GetMapping("/api/cadastre/{x}/{y}")
    @ResponseBody
    public Response radon(@PathVariable("x") Double x, @PathVariable("y") Double y) {
        
        List<ParcelleDTO> parcelleDTOs = gestionParcelleFacade.rechercherParcellesDansRayon(x, y, 0.006);
        
        List<String> ewkts = parcelleDTOs.stream().map(ParcelleDTO::getEwkt).collect(Collectors.toList());
        
        return Response.ok(ewkts).build();
    }
}
