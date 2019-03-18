package fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces;


import fr.gouv.beta.fabnum.commun.metier.IAbstractCRUDService;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.SiteSolPolue;

/**
 * Interface du Service qui gère les entités SiteSolPolue
 */
public interface ISiteSolPolueService extends IAbstractCRUDService<SiteSolPolue> {
    
    SiteSolPolue rechercherZoneContenantParcelle(String codeParcelle);
}
  