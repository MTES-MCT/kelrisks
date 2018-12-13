package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.avis.impl;

import fr.gouv.beta.fabnum.commun.facade.AbstractFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.avis.AvisDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.ParcelleDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteSolPolueDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.avis.IGestionAvisFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionAdresseFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionInstallationClasseeFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionParcelleFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionSiteIndustrielBasiasFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionSiteIndustrielBasolFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionSiteSolPolueFacade;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo.ParcelleQO;

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
    IGestionParcelleFacade             gestionParcelleFacade;
    
    @Override
    public AvisDTO rendreAvis(String codeParcelle, String codePostal, String rue, String numero, String nomProprietaire) {
        
        AvisDTO avisDTO = new AvisDTO();
        
        // Recherche d'une parcelle à partir de l'adresse si aucune n'a été fournie
        ParcelleDTO parcelleDTO;
        if (codeParcelle == null) {
            parcelleDTO = gestionParcelleFacade.rechercherParcelleAvecAdresse(codePostal, rue, numero);
            codeParcelle = parcelleDTO.getCode();
        }
        else {
            ParcelleQO parcelleQO = new ParcelleQO();
            parcelleQO.setCode(codeParcelle);
            parcelleDTO = gestionParcelleFacade.rechercherResultatUniqueAvecCritere(parcelleQO);
        }
        
        // Recherche d'une éventuelle zone poluée contenant la parcelle
        Geometry        geometry;
        SiteSolPolueDTO siteSolPolueDTO = gestionSiteSolPolueFacade.rechercherZoneContenantParcelle(codeParcelle);
        if (siteSolPolueDTO != null) { geometry = siteSolPolueDTO.getMultiPolygon(); }
        else { geometry = parcelleDTO.getMultiPolygon(); }
        
        avisDTO.setSiteIndustrielBasiasSurParcelleDTOs(gestionSiteIndustrielBasiasFacade.rechercherSitesDansPolygon(geometry));
        avisDTO.setSiteIndustrielBasiasAutourParcelleDTOs(gestionSiteIndustrielBasiasFacade.rechercherSiteDansRayonCentroideParcelle(codeParcelle, 100D));
        avisDTO.setSiteIndustrielBasiasParRaisonSocialeDTOs(gestionSiteIndustrielBasiasFacade.rechercherSitesParRaisonSociale(nomProprietaire));
        avisDTO.getSiteIndustrielBasiasAutourParcelleDTOs().removeAll(avisDTO.getSiteIndustrielBasiasSurParcelleDTOs());
        avisDTO.getSiteIndustrielBasiasParRaisonSocialeDTOs().removeAll(avisDTO.getSiteIndustrielBasiasSurParcelleDTOs());
        avisDTO.getSiteIndustrielBasiasParRaisonSocialeDTOs().removeAll(avisDTO.getSiteIndustrielBasiasAutourParcelleDTOs());
        
        avisDTO.setSiteIndustrielBasolSurParcelleDTOs(gestionSiteIndustrielBasolFacade.rechercherSitesDansPolygon(geometry));
        avisDTO.setSiteIndustrielBasolAutourParcelleDTOs(gestionSiteIndustrielBasolFacade.rechercherSiteDansRayonCentroideParcelle(codeParcelle, 100D));
        avisDTO.getSiteIndustrielBasolAutourParcelleDTOs().removeAll(avisDTO.getSiteIndustrielBasolSurParcelleDTOs());
        
        avisDTO.setInstallationClasseeSurParcelleDTOs(gestionInstallationClasseeFacade.rechercherInstallationsDansPolygon(geometry));
        avisDTO.setInstallationClasseeAutourParcelleDTOs(gestionInstallationClasseeFacade.rechercherInstallationsDansRayonCentroideParcelle(codeParcelle, 100D));
        avisDTO.getInstallationClasseeAutourParcelleDTOs().removeAll(avisDTO.getInstallationClasseeSurParcelleDTOs());
        avisDTO.setInstallationClasseeNonGeorerenceesDTOs(gestionInstallationClasseeFacade.rechercherInstallationsAuCentroideCommune(codePostal));
        
        return avisDTO;
    }
}
