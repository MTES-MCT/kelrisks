package fr.gouv.beta.fabnum.kelrisks.presentation.controller;

import fr.gouv.beta.fabnum.kelrisks.facade.avis.AvisDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.ParcelleDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionParcelleFacade;
import fr.gouv.beta.fabnum.kelrisks.presentation.rest.AbstractBasicApi;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;

import javax.ws.rs.core.Response;

import org.apache.commons.lang3.StringUtils;
import org.geolatte.geom.Geometry;
import org.geolatte.geom.crs.CoordinateSystemAxis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CadastreController extends AbstractBasicApi {
    
    @Autowired
    IGestionParcelleFacade gestionParcelleFacade;
    
    @GetMapping("/api/cadastre/{x}/{y}")
    @ResponseBody
    public Response parcelleDansRayon(@PathVariable("x") Double x, @PathVariable("y") Double y) {
        
        List<ParcelleDTO> parcelleDTOs = gestionParcelleFacade.rechercherParcellesDansRayon(x, y, 0.006);
        
        List<String> ewkts = parcelleDTOs.stream().map(ParcelleDTO::getEwkt).collect(Collectors.toList());
        
        return Response.ok(ewkts).build();
    }
    
    @GetMapping("/api/cadastre/proximite/{x}/{y}")
    public Response parcelleLaPlusProche(@PathVariable("x") Double x, @PathVariable("y") Double y) {
        
        ParcelleDTO parcelleDTO = gestionParcelleFacade.rechercherParcelleAvecCoordonnees(x, y);
        
        return Response.ok(parcelleDTO).build();
    }
    
    @GetMapping("/api/cadastre/match/{insee}/{code}")
    public Response parcelleMatch(@PathVariable("insee") String insee, @PathVariable("code") String code) {
    
        if (StringUtils.isEmpty(insee) || StringUtils.isEmpty(code)) { return Response.ok(null).build(); }
    
        List<ParcelleDTO> parcelles = getParcelles(insee, code);
        if (parcelles != null && !parcelles.isEmpty()) {
            ParcelleWithCentroidDTO parcelleWithCentroid = new ParcelleWithCentroidDTO();
            parcelleWithCentroid.setParcelle(parcelles.get(0));
            Geometry<?> centroid = gestionParcelleFacade.rechercherCentroidParcelle(parcelleWithCentroid.getParcelle().getMultiPolygon());
            AvisDTO.Leaflet.Point point = new AvisDTO.Leaflet.Point(Double.toString(centroid.getPositionN(0).getCoordinate(CoordinateSystemAxis.mkLonAxis())),
                                                                    Double.toString(centroid.getPositionN(0).getCoordinate(CoordinateSystemAxis.mkLatAxis())));
            parcelleWithCentroid.setCentroid(point);
        
            return Response.ok(parcelleWithCentroid).build();
        }
        
        return Response.ok(null).build();
    }
    
    @Data
    public static class ParcelleWithCentroidDTO {
        
        private ParcelleDTO           parcelle;
        private AvisDTO.Leaflet.Point centroid = new AvisDTO.Leaflet.Point();
    }
}
