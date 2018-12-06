package fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces;


import fr.gouv.beta.fabnum.commun.metier.IAbstractCRUDService;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.SiteIndustrielBasol;

import java.util.List;

/**
 * Interface du Service qui gère les entités SiteIndustrielBasol
 */
public interface ISiteIndustrielBasolService extends IAbstractCRUDService<SiteIndustrielBasol> {
    
    List<SiteIndustrielBasol> rechercherSiteSurParcelle(String codeParcelle);
    
    List<SiteIndustrielBasol> rechercherSiteDansRayonCentroideParcelle(String codeParcelle, Double distance);
}
  