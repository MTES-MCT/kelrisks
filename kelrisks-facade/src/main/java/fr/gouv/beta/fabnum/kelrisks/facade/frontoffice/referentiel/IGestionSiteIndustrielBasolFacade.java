package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel;

import fr.gouv.beta.fabnum.commun.transverse.qo.AbstractQO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteIndustrielBasolDTO;

import java.util.List;

import org.geolatte.geom.Geometry;

public interface IGestionSiteIndustrielBasolFacade {
    
    List<SiteIndustrielBasolDTO> rechercherSitesSurParcelle(String codeParcelle);
    
    List<SiteIndustrielBasolDTO> rechercherSiteDansRayonCentroideParcelle(String codeParcelle, double distance);
    
    List<SiteIndustrielBasolDTO> rechercherSitesDansPolygons(List<Geometry<?>> multiPolygon);
    
    List<SiteIndustrielBasolDTO> rechercherSitesSurParcelles(List<String> codes);
    
    List<SiteIndustrielBasolDTO> rechercherAvecCritere(AbstractQO... criteres);
    
    List<SiteIndustrielBasolDTO> rechercherSitesDansPolygon(Geometry<?> polygon);
    
    List<SiteIndustrielBasolDTO> rechercherSitesAvecFaiblePrecisionDeGeolocalisation(String codeINSEE);
}