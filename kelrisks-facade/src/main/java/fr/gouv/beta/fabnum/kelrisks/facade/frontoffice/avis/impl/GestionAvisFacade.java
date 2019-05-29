package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.avis.impl;

import fr.gouv.beta.fabnum.commun.facade.AbstractFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.avis.AvisDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.ParcelleDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteSolPolueDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.avis.IGestionAvisFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionAdresseFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionCommuneFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionInstallationClasseeFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionParcelleFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionSiteIndustrielBasiasFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionSiteIndustrielBasolFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionSiteSolPolueFacade;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo.ParcelleQO;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.geolatte.geom.Geometry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestionAvisFacade extends AbstractFacade implements IGestionAvisFacade {
    
    @Autowired
    IGestionSiteSolPolueFacade         gestionSiteSolPolueFacade;
    @Autowired
    IGestionSiteIndustrielBasiasFacade gestionSiteIndustrielBasiasFacade;
    @Autowired
    IGestionSiteIndustrielBasolFacade  gestionSiteIndustrielBasolFacade;
    @Autowired
    IGestionInstallationClasseeFacade  gestionInstallationClasseeFacade;
    @Autowired
    IGestionAdresseFacade              gestionAdresseFacade;
    @Autowired
    IGestionCommuneFacade              gestionCommuneFacade;
    @Autowired
    IGestionParcelleFacade             gestionParcelleFacade;
    
    @Override
    public AvisDTO rendreAvis(String codeParcelle, String codeINSEE, String nomAdresse, String geolocAdresse, String nomProprietaire) {
        
        AvisDTO avisDTO = new AvisDTO();
    
        avisDTO.getSummary().setCommune(gestionCommuneFacade.rechercherCommuneAvecCodeINSEE(codeINSEE));
        avisDTO.getSummary().setNomProprietaire(nomProprietaire);
        
        // Recherche d'une parcelle à partir de l'adresse si aucune n'a été fournie
        ParcelleDTO parcelleDTO;
        if (codeParcelle == null || codeParcelle.equals("")) {
    
            parcelleDTO = gestionParcelleFacade.rechercherParcelleAvecCoordonnees(Double.parseDouble(geolocAdresse.split("\\|")[0]),
                                                                                  Double.parseDouble(geolocAdresse.split("\\|")[1]));
            avisDTO.getSummary().setAdresse(nomAdresse);
            codeParcelle = parcelleDTO.getCode();
        }
        else {
            ParcelleQO parcelleQO = new ParcelleQO();
            parcelleQO.setCode(codeParcelle);
            List<ParcelleDTO> parcelleDTOS = gestionParcelleFacade.rechercherAvecCritere(parcelleQO);
    
            if (parcelleDTOS.isEmpty()) {
                avisDTO.addError("Aucune parcelle n'a été trouvée avec le code donné !");
                return avisDTO;
            }
            if (parcelleDTOS.size() > 1) {
                avisDTO.addError("Plusieurs parcelles ont été trouvées avec le code donné !");
                return avisDTO;
            }
            parcelleDTO = parcelleDTOS.get(0);
        }
    
        avisDTO.getSummary().setCodeParcelle(parcelleDTO.getSection() + "-" + parcelleDTO.getNumero());
        
        // Recherche d'une éventuelle zone poluée contenant la parcelle
        List<Geometry>        geometries       = new ArrayList<>();
        List<SiteSolPolueDTO> siteSolPolueDTOs = gestionSiteSolPolueFacade.rechercherZoneContenantParcelle(codeParcelle);
        if (!siteSolPolueDTOs.isEmpty()) {siteSolPolueDTOs.forEach(siteSolPolueDTO -> geometries.add(siteSolPolueDTO.getMultiPolygon()));}
        else { geometries.add(parcelleDTO.getMultiPolygon()); }
        
        List<String> codesParcellesContigues = gestionParcelleFacade.rechercherParcellesContigues(parcelleDTO.getMultiPolygon()).stream().map(ParcelleDTO::getCode).collect(Collectors.toList());
    
        avisDTO.setSiteIndustrielBasiasSurParcelleDTOs(gestionSiteIndustrielBasiasFacade.rechercherSitesDansPolygons(geometries));
        avisDTO.setSiteIndustrielBasiasProximiteParcelleDTOs(gestionSiteIndustrielBasiasFacade.rechercherSitesSurParcelles(codesParcellesContigues));
        avisDTO.setSiteIndustrielBasiasRayonParcelleDTOs(gestionSiteIndustrielBasiasFacade.rechercherSiteDansRayonCentroideParcelle(codeParcelle, 100D));
        if (!nomProprietaire.equals("")) {
            avisDTO.setSiteIndustrielBasiasParRaisonSocialeDTOs(gestionSiteIndustrielBasiasFacade.rechercherParNomProprietaireDansRayonGeometry(parcelleDTO.getMultiPolygon(), nomProprietaire, 5000D));
        }
        avisDTO.getSiteIndustrielBasiasRayonParcelleDTOs().removeAll(avisDTO.getSiteIndustrielBasiasSurParcelleDTOs());
        avisDTO.getSiteIndustrielBasiasRayonParcelleDTOs().removeAll(avisDTO.getSiteIndustrielBasiasProximiteParcelleDTOs());
        
        avisDTO.getSiteIndustrielBasiasParRaisonSocialeDTOs().removeAll(avisDTO.getSiteIndustrielBasiasSurParcelleDTOs());
        avisDTO.getSiteIndustrielBasiasParRaisonSocialeDTOs().removeAll(avisDTO.getSiteIndustrielBasiasProximiteParcelleDTOs());
        avisDTO.getSiteIndustrielBasiasParRaisonSocialeDTOs().removeAll(avisDTO.getSiteIndustrielBasiasRayonParcelleDTOs());
    
        avisDTO.setSiteIndustrielBasolSurParcelleDTOs(gestionSiteIndustrielBasolFacade.rechercherSitesDansPolygon(geometries));
        avisDTO.setSiteIndustrielBasolProximiteParcelleDTOs(gestionSiteIndustrielBasolFacade.rechercherSitesSurParcelles(codesParcellesContigues));
        avisDTO.setSiteIndustrielBasolRayonParcelleDTOs(gestionSiteIndustrielBasolFacade.rechercherSiteDansRayonCentroideParcelle(codeParcelle, 100D));
    
        avisDTO.getSiteIndustrielBasolRayonParcelleDTOs().removeAll(avisDTO.getSiteIndustrielBasolSurParcelleDTOs());
        avisDTO.getSiteIndustrielBasolRayonParcelleDTOs().removeAll(avisDTO.getSiteIndustrielBasolProximiteParcelleDTOs());
    
        avisDTO.setInstallationClasseeSurParcelleDTOs(gestionInstallationClasseeFacade.rechercherInstallationsDansPolygon(geometries));
        avisDTO.setInstallationClasseeProximiteParcelleDTOs(gestionInstallationClasseeFacade.rechercherInstallationsSurParcelles(codesParcellesContigues));
        avisDTO.setInstallationClasseeRayonParcelleDTOs(gestionInstallationClasseeFacade.rechercherInstallationsDansRayonCentroideParcelle(codeParcelle, 100D));
    
        avisDTO.getInstallationClasseeRayonParcelleDTOs().removeAll(avisDTO.getInstallationClasseeSurParcelleDTOs());
        avisDTO.getInstallationClasseeRayonParcelleDTOs().removeAll(avisDTO.getInstallationClasseeProximiteParcelleDTOs());
        
        avisDTO.setInstallationClasseeNonGeorerenceesDTOs(gestionInstallationClasseeFacade.rechercherInstallationsAuCentroideCommune(codeINSEE));
        
        return avisDTO;
    }
}
