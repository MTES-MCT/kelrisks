package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel;


import fr.gouv.beta.fabnum.commun.persistance.IAbstractDAO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.SiteIndustrielBasol;

import java.util.List;

/**
 * Classe interface d'accès DAO à SiteIndustrielBasol
 */
public interface ISiteIndustrielBasolDAO extends IAbstractDAO<SiteIndustrielBasol> {
    
    List<SiteIndustrielBasol> rechercherSiteSurParcelle(String codeParcelle);
    
    List<SiteIndustrielBasol> rechercherSiteDansRayonCentroideParcelle(String codeParcelle, Double distance);
}
  