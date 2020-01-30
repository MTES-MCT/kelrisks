package fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces;


import fr.gouv.beta.fabnum.commun.metier.IAbstractCRUDService;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.SiteIndustrielBasol;

import java.util.List;

import org.geolatte.geom.Geometry;

/**
 * Interface du Service qui gère les entités SiteIndustrielBasol
 */
public interface ISiteIndustrielBasolService extends IAbstractCRUDService<SiteIndustrielBasol> {
    
    List<SiteIndustrielBasol> rechercherSiteSurParcelle(String codeParcelle);
    
    List<SiteIndustrielBasol> rechercherSiteDansRayonCentroideParcelle(String codeParcelle, double distance);
    
    List<SiteIndustrielBasol> rechercherSitesDansPolygons(List<Geometry<?>> multiPolygon);
    
    List<SiteIndustrielBasol> rechercherSitesSurParcelles(List<String> codes);
    
    List<SiteIndustrielBasol> rechercherSitesDansPolygon(Geometry<?> polygon);
}
  