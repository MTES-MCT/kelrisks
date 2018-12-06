package fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces;


import fr.gouv.beta.fabnum.commun.metier.IAbstractCRUDService;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.SiteIndustrielBasias;

import java.util.List;

/**
 * Interface du Service qui gère les entités SiteIndustrielBasias
 */
public interface ISiteIndustrielBasiasService extends IAbstractCRUDService<SiteIndustrielBasias> {
    
    List<SiteIndustrielBasias> rechercherSiteSurParcelle(String codeParcelle);
    
    List<SiteIndustrielBasias> rechercherSiteDansRayonCentroideParcelle(String codeParcelle, Double distance);
}
  