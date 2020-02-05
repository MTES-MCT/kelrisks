package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel;

import fr.gouv.beta.fabnum.commun.persistance.IAbstractDAO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.SiteIndustrielBasias;

import java.util.List;

import org.geolatte.geom.Geometry;

/**
 * Classe interface d'accès DAO à SiteIndustrielBasias
 */
public interface ISiteIndustrielBasiasDAO extends IAbstractDAO<SiteIndustrielBasias> {
    
    List<SiteIndustrielBasias> rechercherSiteSurParcelle(String codeParcelle);
    
    List<SiteIndustrielBasias> rechercherSiteDansRayonCentroideParcelle(Geometry<?> geometry, double distance);
    
    List<SiteIndustrielBasias> rechercherSitesDansPolygons(List<Geometry<?>> multiPolygon);
    
    List<SiteIndustrielBasias> rechercherSitesDansPolygon(Geometry<?> multiPolygon);
    
    List<SiteIndustrielBasias> rechercherParNomProprietaireDansRayonGeometry(Geometry<?> geometry, String nomProprietaire, double distance);
    
    List<SiteIndustrielBasias> rechercherRaisonsSociales(String codeINSEE, String query);
    
    List<SiteIndustrielBasias> rechercherSitesSurParcelles(List<String> codes);
}
  