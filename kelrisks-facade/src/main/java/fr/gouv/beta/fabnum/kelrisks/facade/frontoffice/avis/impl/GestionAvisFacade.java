package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.avis.impl;

import fr.gouv.beta.fabnum.commun.facade.AbstractFacade;
import fr.gouv.beta.fabnum.commun.utils.GeoJsonUtils;
import fr.gouv.beta.fabnum.kelrisks.facade.avis.AvisDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.ParcelleDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteSolPolueDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.avis.IGestionAvisFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionCommuneFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionGeorisquesFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionInstallationClasseeFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionParcelleFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionSecteurInformationSolFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionSiteIndustrielBasiasFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionSiteIndustrielBasolFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionSiteSolPolueFacade;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedRadon;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedSismique;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo.ParcelleQO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo.SiteIndustrielBasolParcelleQO;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.geolatte.geom.G2D;
import org.geolatte.geom.Geometry;
import org.geolatte.geom.Point;
import org.geolatte.geom.Positions;
import org.geolatte.geom.crs.CoordinateReferenceSystems;
import org.geolatte.geom.crs.CoordinateSystemAxis;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GestionAvisFacade extends AbstractFacade implements IGestionAvisFacade {
    
    @Autowired
    IGestionSiteSolPolueFacade          gestionSiteSolPolueFacade;
    @Autowired
    IGestionSiteIndustrielBasiasFacade  gestionSiteIndustrielBasiasFacade;
    @Autowired
    IGestionSiteIndustrielBasolFacade   gestionSiteIndustrielBasolFacade;
    @Autowired
    IGestionInstallationClasseeFacade   gestionInstallationClasseeFacade;
    @Autowired
    IGestionCommuneFacade               gestionCommuneFacade;
    @Autowired
    IGestionParcelleFacade              gestionParcelleFacade;
    @Autowired
    IGestionSecteurInformationSolFacade gestionSecteurInformationSolFacade;
    @Autowired
    IGestionGeorisquesFacade            gestionGeorisquesFacade;
    
    @Override
    public AvisDTO rendreAvis(String codeParcelle, String codeINSEE, String nomAdresse, String geolocAdresse, String nomProprietaire) {
        
        AvisDTO avisDTO = new AvisDTO();
    
        //        TODO : Modifier si le périmètre change
        if (!codeINSEE.matches("(?:75|77|78|91|92|93|94|95)\\d{3}")) {
            avisDTO.addWarning("Le territoire d'expérimentation de Kelrisks est pour l'instant limité à l'Île de France.");
            return avisDTO;
        }
    
        avisDTO.getSummary().setCommune(gestionCommuneFacade.rechercherCommuneAvecCodeINSEE(codeINSEE));
        avisDTO.getSummary().setNomProprietaire(nomProprietaire);
        
        // Recherche d'une parcelle à partir de l'adresse si aucune n'a été fournie
        ParcelleDTO parcelleDTO;
        if (codeParcelle == null || codeParcelle.equals("")) {
    
            parcelleDTO = gestionParcelleFacade.rechercherParcelleAvecCoordonnees(Double.parseDouble(geolocAdresse.split("\\|")[0]),
                                                                                  Double.parseDouble(geolocAdresse.split("\\|")[1]));
    
            Positions.CanMakeG2D canMakeG2D = new Positions.CanMakeG2D();
            G2D g2D = canMakeG2D.mkPosition(Double.parseDouble(geolocAdresse.split("\\|")[0]),
                                            Double.parseDouble(geolocAdresse.split("\\|")[1]));
            Point point = new Point(g2D, CoordinateReferenceSystems.WGS84);
            avisDTO.getLeaflet().setAdresse(GeoJsonUtils.toGeoJson(point, Stream.of(new AbstractMap.SimpleEntry<>("adresse", nomAdresse))
                                                                                  .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))));
            avisDTO.getSummary().setAdresse(nomAdresse);
    
            codeParcelle = parcelleDTO.getCode();
    
            avisDTO.getLeaflet().setCenter(new AvisDTO.Leaflet.Point(geolocAdresse.split("\\|")[0],
                                                                     geolocAdresse.split("\\|")[1]));
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
    
            Geometry centroid = gestionParcelleFacade.rechercherCentroidParcelle(parcelleDTO.getMultiPolygon());
    
            avisDTO.getLeaflet().setCenter(new AvisDTO.Leaflet.Point(Double.toString(centroid.getPositionN(0).getCoordinate(CoordinateSystemAxis.mkLonAxis())),
                                                                     Double.toString(centroid.getPositionN(0).getCoordinate(CoordinateSystemAxis.mkLatAxis()))));
        }
    
        Geometry expendedParcelle = gestionParcelleFacade.rechercherExpendedParcelle(parcelleDTO.getCode(), 100);
        Geometry touchesParcelle  = gestionParcelleFacade.rechercherUnionParcellesContigues(parcelleDTO.getMultiPolygon());
        
        avisDTO.getSummary().setCodeParcelle(parcelleDTO.getSection() + "-" + parcelleDTO.getNumero());
        avisDTO.getLeaflet().setParcelle(GeoJsonUtils.toGeoJson(parcelleDTO.getMultiPolygon(),
                                                                Stream.of(new AbstractMap.SimpleEntry<>("parcelle", parcelleDTO.getSection() + "-" + parcelleDTO.getNumero()))
                                                                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))));
        
        // Recherche d'une éventuelle zone poluée contenant la parcelle
        List<Geometry>        parcelleSitesSolsPolues = new ArrayList<>();
        List<SiteSolPolueDTO> siteSolPolueDTOs        = gestionSiteSolPolueFacade.rechercherZoneContenantParcelle(codeParcelle);
        if (!siteSolPolueDTOs.isEmpty()) {
            siteSolPolueDTOs.forEach(siteSolPolueDTO -> {
                parcelleSitesSolsPolues.add(siteSolPolueDTO.getMultiPolygon());
                avisDTO.getLeaflet().getSsp().add(siteSolPolueDTO.getEwkt());
            });
        }
        else { parcelleSitesSolsPolues.add(parcelleDTO.getMultiPolygon()); }
    
        getAvisBasias(avisDTO, parcelleDTO.getMultiPolygon(), parcelleSitesSolsPolues, touchesParcelle, expendedParcelle, nomProprietaire);
    
        getAvisBasol(avisDTO, parcelleDTO, parcelleSitesSolsPolues, touchesParcelle, expendedParcelle);
        
        getAvisICPE(avisDTO, parcelleSitesSolsPolues, touchesParcelle, expendedParcelle, codeINSEE);
    
        getAvisSis(avisDTO, parcelleSitesSolsPolues, touchesParcelle, expendedParcelle);
    
        getAvisSismicite(avisDTO, codeINSEE);
    
        getAvisRadon(avisDTO, codeINSEE);
        
        return avisDTO;
    }
    
    private void getAvisSismicite(AvisDTO avisDTO, String codeINSEE) {
        
        GeorisquePaginatedSismique georisquePaginatedSismique = gestionGeorisquesFacade.rechercherSismiciteCommune(codeINSEE);
        
        if (!georisquePaginatedSismique.getData().isEmpty()) {
            avisDTO.setCodeZoneSismicite(Integer.parseInt(georisquePaginatedSismique.getData().get(0).getCode_zone()));
        }
    }
    
    private void getAvisRadon(AvisDTO avisDTO, String codeINSEE) {
        
        GeorisquePaginatedRadon georisquePaginatedRadon = gestionGeorisquesFacade.rechercherRadonCommune(codeINSEE);
        
        if (!georisquePaginatedRadon.getData().isEmpty()) {
            avisDTO.setClassePotentielRadon(Integer.parseInt(georisquePaginatedRadon.getData().get(0).getClasse_potentiel()));
        }
    }
    
    private void getAvisICPE(AvisDTO avisDTO, List<Geometry> parcelleSitesSolsPolues, Geometry touchesParcelle, Geometry expendedParcelle, String codeINSEE) {
        
        avisDTO.setInstallationClasseeSurParcelleDTOs(gestionInstallationClasseeFacade.rechercherInstallationsDansPolygons(parcelleSitesSolsPolues));
        avisDTO.setInstallationClasseeProximiteParcelleDTOs(gestionInstallationClasseeFacade.rechercherSitesDansPolygon(touchesParcelle));
        avisDTO.setInstallationClasseeRayonParcelleDTOs(gestionInstallationClasseeFacade.rechercherSitesDansPolygon(expendedParcelle));
    
        avisDTO.getInstallationClasseeRayonParcelleDTOs().forEach(icpe -> avisDTO.getLeaflet().getIcpe().add(icpe.getEwkt()));
        
        avisDTO.getInstallationClasseeRayonParcelleDTOs().removeAll(avisDTO.getInstallationClasseeSurParcelleDTOs());
        avisDTO.getInstallationClasseeRayonParcelleDTOs().removeAll(avisDTO.getInstallationClasseeProximiteParcelleDTOs());
    
        avisDTO.getInstallationClasseeProximiteParcelleDTOs().removeAll(avisDTO.getInstallationClasseeSurParcelleDTOs());
        
        avisDTO.setInstallationClasseeNonGeorerenceesDTOs(gestionInstallationClasseeFacade.rechercherInstallationsAuCentroideCommune(codeINSEE));
    }
    
    private void getAvisBasol(AvisDTO avisDTO, ParcelleDTO parcelleDTO, List<Geometry> parcelleSitesSolsPolues, Geometry touchesParcelle, Geometry expendedParcelle) {
        
        List<SiteIndustrielBasolDTO> siteIndustrielBasolSurSSP = gestionSiteIndustrielBasolFacade.rechercherSitesDansPolygons(parcelleSitesSolsPolues);
        
        SiteIndustrielBasolParcelleQO siteIndustrielBasolParcelleQO = new SiteIndustrielBasolParcelleQO();
        siteIndustrielBasolParcelleQO.setParcelleCodeINSEE(parcelleDTO.getCommune());
        siteIndustrielBasolParcelleQO.setParcelleSection(parcelleDTO.getSection() + "01");
        siteIndustrielBasolParcelleQO.setParcelleNumero(parcelleDTO.getNumero());
        
        siteIndustrielBasolSurSSP.addAll(gestionSiteIndustrielBasolFacade.rechercherAvecCritere(siteIndustrielBasolParcelleQO));
        
        avisDTO.setSiteIndustrielBasolSurParcelleDTOs(siteIndustrielBasolSurSSP);
        avisDTO.setSiteIndustrielBasolProximiteParcelleDTOs(gestionSiteIndustrielBasolFacade.rechercherSitesDansPolygon(touchesParcelle));
        avisDTO.setSiteIndustrielBasolRayonParcelleDTOs(gestionSiteIndustrielBasolFacade.rechercherSitesDansPolygon(expendedParcelle));
        
        avisDTO.getSiteIndustrielBasolRayonParcelleDTOs().forEach(sib -> avisDTO.getLeaflet().getBasol().add(sib.getEwkt()));
        
        avisDTO.getSiteIndustrielBasolRayonParcelleDTOs().removeAll(avisDTO.getSiteIndustrielBasolSurParcelleDTOs());
        avisDTO.getSiteIndustrielBasolRayonParcelleDTOs().removeAll(avisDTO.getSiteIndustrielBasolProximiteParcelleDTOs());
    }
    
    private void getAvisSis(AvisDTO avisDTO, List<Geometry> parcelleSitesSolsPolues, Geometry touchesParcelle, Geometry expendedParcelle) {
        
        avisDTO.setSecteurInformationSolSurParcelleDTOs(gestionSecteurInformationSolFacade.rechercherSitesDansPolygons(parcelleSitesSolsPolues));
        avisDTO.setSecteurInformationSolProximiteParcelleDTOs(gestionSecteurInformationSolFacade.rechercherSitesDansPolygon(touchesParcelle));
        avisDTO.setSecteurInformationSolRayonParcelleDTOs(gestionSecteurInformationSolFacade.rechercherSitesDansPolygon(expendedParcelle));
        
        avisDTO.getSecteurInformationSolRayonParcelleDTOs().forEach(sib -> avisDTO.getLeaflet().getSis().add(sib.getEwkt()));
        
        avisDTO.getSecteurInformationSolRayonParcelleDTOs().removeAll(avisDTO.getSecteurInformationSolSurParcelleDTOs());
        avisDTO.getSecteurInformationSolRayonParcelleDTOs().removeAll(avisDTO.getSecteurInformationSolProximiteParcelleDTOs());
    }
    
    private void getAvisBasias(AvisDTO avisDTO, Geometry parcelle, List<Geometry> parcelleSitesSolsPolues, Geometry touchesParcelle, Geometry expendedParcelle, String nomProprietaire) {
        
        avisDTO.setSiteIndustrielBasiasSurParcelleDTOs(gestionSiteIndustrielBasiasFacade.rechercherSitesDansPolygons(parcelleSitesSolsPolues));
        avisDTO.setSiteIndustrielBasiasProximiteParcelleDTOs(gestionSiteIndustrielBasiasFacade.rechercherSitesDansPolygon(touchesParcelle));
        avisDTO.setSiteIndustrielBasiasRayonParcelleDTOs(gestionSiteIndustrielBasiasFacade.rechercherSitesDansPolygon(expendedParcelle));
    
        avisDTO.getSiteIndustrielBasiasRayonParcelleDTOs().forEach(sib -> avisDTO.getLeaflet().getBasias().add(sib.getEwkt()));
        
        if (!nomProprietaire.equals("")) {
            avisDTO.setSiteIndustrielBasiasParRaisonSocialeDTOs(gestionSiteIndustrielBasiasFacade.rechercherParNomProprietaireDansRayonGeometry(parcelle, nomProprietaire, 5000D));
        }
        avisDTO.getSiteIndustrielBasiasRayonParcelleDTOs().removeAll(avisDTO.getSiteIndustrielBasiasSurParcelleDTOs());
        avisDTO.getSiteIndustrielBasiasRayonParcelleDTOs().removeAll(avisDTO.getSiteIndustrielBasiasProximiteParcelleDTOs());
        
        avisDTO.getSiteIndustrielBasiasParRaisonSocialeDTOs().removeAll(avisDTO.getSiteIndustrielBasiasSurParcelleDTOs());
        avisDTO.getSiteIndustrielBasiasParRaisonSocialeDTOs().removeAll(avisDTO.getSiteIndustrielBasiasProximiteParcelleDTOs());
        avisDTO.getSiteIndustrielBasiasParRaisonSocialeDTOs().removeAll(avisDTO.getSiteIndustrielBasiasRayonParcelleDTOs());
    }
}


