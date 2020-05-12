package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel;

import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.ParcelleDTO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo.ParcelleQO;

import java.util.List;

import org.geolatte.geom.Geometry;

public interface IGestionParcelleFacade {
    
    ParcelleDTO rechercherResultatUniqueAvecCritere(ParcelleQO parcelleQO);
    
    List<ParcelleDTO> rechercherAvecCritere(ParcelleQO parcelleQO);
    
    List<ParcelleDTO> rechercherParcellesContigues(Geometry<?> geom);
    
    ParcelleDTO rechercherParcelleAvecCoordonnees(double x, double y);
    
    Geometry<?> rechercherExpendedParcelle(Geometry<?> parcelleGeom, double distance);
    
    Geometry<?> rechercherUnionParcellesContigues(Geometry<?> multiPolygon);
    
    Geometry<?> rechercherCentroidParcelle(Geometry<?> polygon);
    
    ParcelleDTO rechercherParcellesIntersectionnantSurface(Geometry<?> polygon);
    
    List<ParcelleDTO> rechercherParcellesDansRayon(double x, double y, double radius);
    
    Geometry<?> rechercherUnionParcelles(List<Long> ids);
}