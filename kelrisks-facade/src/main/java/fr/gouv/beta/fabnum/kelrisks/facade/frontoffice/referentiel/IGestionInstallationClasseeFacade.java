package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel;

import fr.gouv.beta.fabnum.commun.facade.dto.AutocompleteDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.InstallationClasseeDTO;

import java.util.List;

import org.geolatte.geom.Geometry;

public interface IGestionInstallationClasseeFacade {
    
    List<InstallationClasseeDTO> rechercherInstallationsSurParcelle(String codeParcelle);
    
    List<InstallationClasseeDTO> rechercherInstallationsDansRayonCentroideParcelle(String codeParcelle, double distance);
    
    List<InstallationClasseeDTO> rechercherInstallationsAvecFaiblePrecisionDeGeolocalisation(String codePostal);
    
    List<InstallationClasseeDTO> rechercherInstallationsDansPolygons(List<Geometry<?>> codeParcelle);
    
    List<InstallationClasseeDTO> rechercherInstallationsSurParcelles(List<String> codes);
    
    List<AutocompleteDTO> rechercherRaisonsSociales(String codeINSEE, String query);
    
    List<InstallationClasseeDTO> rechercherSitesDansPolygon(Geometry<?> polygon);
}