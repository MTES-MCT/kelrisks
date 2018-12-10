package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel;


import fr.gouv.beta.fabnum.commun.persistance.IAbstractDAO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.SiteSolPolue;

/**
 * Classe interface d'accès DAO à SiteSolPolue
 */
public interface ISiteSolPolueDAO extends IAbstractDAO<SiteSolPolue> {
    
    SiteSolPolue rechercherZoneContenantParcelle(String codeParcelle);
}
  