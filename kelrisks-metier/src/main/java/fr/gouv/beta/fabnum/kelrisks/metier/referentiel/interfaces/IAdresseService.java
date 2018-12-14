package fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces;


import fr.gouv.beta.fabnum.commun.metier.IAbstractCRUDService;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.Adresse;

import java.util.List;

import org.geolatte.geom.Geometry;

/**
 * Interface du Service qui gère les entités Adresse
 */
public interface IAdresseService extends IAbstractCRUDService<Adresse> {
    
    List<Adresse> rechercherAdresseDansGeometry(Geometry geometry);
    
    List<Adresse> rechercherCommunePartielle(String query);
}
  