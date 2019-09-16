package fr.gouv.beta.fabnum.kelrisks.presentation.controller;

import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionGeorisquesFacade;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedRadon;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedSismique;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class GeorisqueController {
    
    @Autowired
    IGestionGeorisquesFacade georisquesFacade;
    
    @GetMapping("/georisques/radon/{insee}")
    public GeorisquePaginatedRadon radon(@PathVariable("insee") String codeINSEE) {
    
        return georisquesFacade.rechercherRadonCommune(codeINSEE);
    }
    
    @GetMapping("/georisques/sismique/{insee}")
    public GeorisquePaginatedSismique sismique(@PathVariable("insee") String codeINSEE) {//String codeINSEE, double lat, double lon) {
    
        return georisquesFacade.rechercherSismiciteCommune(codeINSEE);
    }
}
