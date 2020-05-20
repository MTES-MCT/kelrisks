package fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces;

import fr.gouv.beta.fabnum.commun.metier.IAbstractCRUDService;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.Commune;

import java.util.List;

import org.geolatte.geom.Geometry;

/**
 * Interface du Service qui gère les entités Commune
 */
public interface ICommuneService extends IAbstractCRUDService<Commune> {
    
    List<Commune> rechercherCommunesLimitrophes(Geometry<?> geog, String notINSEE);
}
  