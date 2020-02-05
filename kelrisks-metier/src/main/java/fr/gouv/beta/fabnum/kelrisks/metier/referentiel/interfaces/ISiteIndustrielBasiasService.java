package fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces;

import fr.gouv.beta.fabnum.commun.metier.IAbstractCRUDService;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.SiteIndustrielBasias;

import java.util.List;

import org.geolatte.geom.Geometry;

/**
 * Interface du Service qui gère les entités SiteIndustrielBasias
 */
public interface ISiteIndustrielBasiasService extends IAbstractCRUDService<SiteIndustrielBasias> {
    
    List<SiteIndustrielBasias> rechercherSiteSurParcelle(String codeParcelle);
    
    List<SiteIndustrielBasias> rechercherSiteDansRayonCentroideGeometry(Geometry<?> codeParcelle, double distance);
    
    List<SiteIndustrielBasias> rechercherSitesDansPolygons(List<Geometry<?>> multiPolygon);
    
    List<SiteIndustrielBasias> rechercherSitesDansPolygon(Geometry<?> multiPolygon);
    
    List<SiteIndustrielBasias> rechercherParNomProprietaireDansRayonGeometry(Geometry<?> geometry, String nomProprietaire, double distance);
    
    List<SiteIndustrielBasias> rechercherRaisonsSociales(String codeINSEE, String query);
    
    List<SiteIndustrielBasias> rechercherSitesSurParcelles(List<String> codes);
}
  