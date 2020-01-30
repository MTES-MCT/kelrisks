package fr.gouv.beta.fabnum.kelrisks.persistance.referentiel;

import fr.gouv.beta.fabnum.commun.persistance.IAbstractDAO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.SecteurInformationSol;

import java.util.List;

import org.geolatte.geom.Geometry;

/**
 * Classe interface d'accès DAO à SecteurInformationSol
 */
public interface ISecteurInformationSolDAO extends IAbstractDAO<SecteurInformationSol> {
    
    List<SecteurInformationSol> rechercherSecteursDansPolygon(Geometry<?> polygon);
    
    List<SecteurInformationSol> rechercherSecteursDansPolygons(List<Geometry<?>> polygons);
}
  