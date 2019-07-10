package fr.gouv.beta.fabnum.kelrisks.metier.referentiel.interfaces;

import fr.gouv.beta.fabnum.commun.metier.IAbstractCRUDService;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.SecteurInformationSol;

import java.util.List;

import org.geolatte.geom.Geometry;

/**
 * Interface du Service qui gère les entités SecteurInformationSol
 */
public interface ISecteurInformationSolService extends IAbstractCRUDService<SecteurInformationSol> {
    
    List<SecteurInformationSol> rechercherSecteursDansPolygon(Geometry geometry);
    
    List<SecteurInformationSol> rechercherSecteursDansPolygons(List<Geometry> geometry);
}
  