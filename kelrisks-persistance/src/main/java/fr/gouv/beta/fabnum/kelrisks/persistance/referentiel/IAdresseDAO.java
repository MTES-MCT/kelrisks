package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel;


import fr.gouv.beta.fabnum.commun.persistance.IAbstractDAO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.Adresse;

import java.util.List;

import org.geolatte.geom.Geometry;

/**
 * Classe interface d'accès DAO à Adresse
 */
public interface IAdresseDAO extends IAbstractDAO<Adresse> {
    
    List<Adresse> rechercherAdresseDansGeometry(Geometry geometry);
    
    List<Adresse> rechercherCommunePartielle(String query);
}
  