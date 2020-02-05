package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.avis.impl;

import fr.gouv.beta.fabnum.commun.facade.AbstractFacade;
import fr.gouv.beta.fabnum.commun.utils.GeoJsonUtils;
import fr.gouv.beta.fabnum.kelrisks.facade.avis.AvisDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.AbstractLocalisationAvecPrecision;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.CommuneDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.InstallationClasseeDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.ParcelleDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.PlanPreventionRisquesGasparDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SecteurInformationSolDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteIndustrielBasiasDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteIndustrielBasolDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteSolPolueDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.avis.IGestionAvisFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionCommuneFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionGeoDataGouvFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionGeorisquesFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionIGNCartoFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionInstallationClasseeFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionParcelleFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionPlanPreventionRisquesGasparFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionSiteIndustrielBasiasFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionSiteIndustrielBasolFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionSiteSolPolueFacade;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedRadon;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedSIS;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedSismique;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.IGNCartoAssiettePaginatedFeatures;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.IGNCartoGenerateurPaginatedFeatures;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.enums.PrecisionEnum;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo.ParcelleQO;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo.PlanPreventionRisquesGasparQO;

import java.text.SimpleDateFormat;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.constraints.NotNull;

import org.apache.commons.lang3.StringUtils;
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
    IGestionSiteSolPolueFacade                gestionSiteSolPolueFacade;
    @Autowired
    IGestionSiteIndustrielBasiasFacade        gestionSiteIndustrielBasiasFacade;
    @Autowired
    IGestionSiteIndustrielBasolFacade         gestionSiteIndustrielBasolFacade;
    @Autowired
    IGestionInstallationClasseeFacade         gestionInstallationClasseeFacade;
    @Autowired
    IGestionCommuneFacade                     gestionCommuneFacade;
    @Autowired
    IGestionParcelleFacade                    gestionParcelleFacade;
    @Autowired
    IGestionGeorisquesFacade                  gestionGeorisquesFacade;
    @Autowired
    IGestionGeoDataGouvFacade                 gestionGeoDataGouvFacade;
    @Autowired
    IGestionIGNCartoFacade                    gestionIGNCartoFacade;
    @Autowired
    IGestionPlanPreventionRisquesGasparFacade gestionPlanPreventionRisquesGasparFacade;
    
    @Override
    public AvisDTO rendreAvis(String codeParcelle, CommuneDTO communeDTO, @NotNull String nomAdresse, @NotNull String geolocAdresse, @NotNull String nomProprietaire) {
        
        AvisDTO avisDTO = new AvisDTO();
        
        avisDTO.getSummary().setCommune(communeDTO);
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
    
            if (parcelleDTO == null) {
                avisDTO.addError("Aucune parcelle n'a été trouvée à l'addresse indiquée !");
                return avisDTO;
            }
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
    
        Point<?> centroid = (Point<?>) gestionParcelleFacade.rechercherCentroidParcelle(parcelleDTO.getMultiPolygon());
    
        avisDTO.getLeaflet().setCenter(new AvisDTO.Leaflet.Point(Double.toString(centroid.getPositionN(0).getCoordinate(CoordinateSystemAxis.mkLonAxis())),
                                                                 Double.toString(centroid.getPositionN(0).getCoordinate(CoordinateSystemAxis.mkLatAxis()))));
    
        return getAvisFromParcelle(avisDTO, parcelleDTO, centroid, communeDTO, nomProprietaire);
    }
    
    @Override
    public AvisDTO rendreAvis(String geoJson) {
    
        AvisDTO avisDTO = new AvisDTO();
    
        Geometry<?> geometry = GeoJsonUtils.fromGeoJson(geoJson);
    
        ParcelleDTO parcelleDTO = new ParcelleDTO();
        parcelleDTO.setMultiPolygon(geometry);
        parcelleDTO.setEwkt(GeoJsonUtils.toGeoJson(geometry));
    
        Point<?> centroid = (Point<?>) gestionParcelleFacade.rechercherCentroidParcelle(geometry);
    
        CommuneDTO communeDTO = gestionGeoDataGouvFacade.rechercherCommune(Double.toString(((Point) centroid).getPosition().getCoordinate(CoordinateSystemAxis.mkLatAxis())),
                                                                           Double.toString(((Point) centroid).getPosition().getCoordinate(CoordinateSystemAxis.mkLonAxis())));
    
        avisDTO.getSummary().setCommune(communeDTO);
    
        return getAvisFromParcelle(avisDTO, parcelleDTO, centroid, communeDTO, null);
    }
    
    private AvisDTO getAvisFromParcelle(AvisDTO avisDTO, ParcelleDTO parcelleDTO, Point<?> centroid, CommuneDTO communeDTO, String nomProprietaire) {
        
        Geometry<?> expendedParcelle = gestionParcelleFacade.rechercherExpendedParcelle(parcelleDTO.getCode(), 100);
        Geometry<?> touchesParcelle  = gestionParcelleFacade.rechercherUnionParcellesContigues(parcelleDTO.getMultiPolygon());
        
        avisDTO.getSummary().setCodeParcelle(parcelleDTO.getSection() + "-" + parcelleDTO.getNumero());
        avisDTO.getLeaflet().setParcelle(GeoJsonUtils.toGeoJson(parcelleDTO.getMultiPolygon(),
                                                                Stream.of(new AbstractMap.SimpleEntry<>("parcelle", parcelleDTO.getSection() + "-" + parcelleDTO.getNumero()))
                                                                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))));
        
        // Recherche d'une éventuelle zone poluée contenant la parcelle
        List<Geometry<?>>     parcelleSitesSolsPolues = new ArrayList<>();
        List<SiteSolPolueDTO> siteSolPolueDTOs        = gestionSiteSolPolueFacade.rechercherZoneContenantParcelle(parcelleDTO.getCode());
        if (!siteSolPolueDTOs.isEmpty()) {
            siteSolPolueDTOs.forEach(siteSolPolueDTO -> {
                parcelleSitesSolsPolues.add(siteSolPolueDTO.getMultiPolygon());
                avisDTO.getLeaflet().getSsp().add(siteSolPolueDTO.getEwkt());
            });
        }
        else { parcelleSitesSolsPolues.add(parcelleDTO.getMultiPolygon()); }
        
        getAvisBasias(avisDTO, parcelleDTO.getMultiPolygon(), parcelleSitesSolsPolues, touchesParcelle, expendedParcelle, nomProprietaire, communeDTO.getCodeINSEE());
        
        getAvisBasol(avisDTO, parcelleSitesSolsPolues, touchesParcelle, expendedParcelle, communeDTO.getCodeINSEE());
        
        getAvisICPE(avisDTO, parcelleSitesSolsPolues, touchesParcelle, expendedParcelle, communeDTO.getCodeINSEE());
        
        getAvisSis(avisDTO, centroid);
        
        getAvisPPR(avisDTO, parcelleSitesSolsPolues, communeDTO.getCodeINSEE());
        
        getAvisSismicite(avisDTO, communeDTO.getCodeINSEE());
        
        getAvisRadon(avisDTO, communeDTO.getCodeINSEE());
        
        return avisDTO;
    }
    
    private void getAvisSis(AvisDTO avisDTO, Point<?> centroid) {
        
        GeorisquePaginatedSIS georisquePaginatedSisParcelle = gestionGeorisquesFacade.rechercherSisCoordonnees(centroid.getPositionN(0).getCoordinate(CoordinateSystemAxis.mkLonAxis()),
                                                                                                               centroid.getPositionN(0).getCoordinate(CoordinateSystemAxis.mkLatAxis()));
        GeorisquePaginatedSIS georisquePaginatedSisRayonParcelle = gestionGeorisquesFacade.rechercherSisCoordonneesRayon(centroid.getPositionN(0).getCoordinate(CoordinateSystemAxis.mkLonAxis()),
                                                                                                                         centroid.getPositionN(0).getCoordinate(CoordinateSystemAxis.mkLatAxis()),
                                                                                                                         100);
        
        if (!georisquePaginatedSisParcelle.getData().isEmpty()) {
    
            SecteurInformationSolDTO secteurInformationSolDTO = new SecteurInformationSolDTO();
    
            secteurInformationSolDTO.setId(georisquePaginatedSisParcelle.getData().get(0).getId_sis());
            secteurInformationSolDTO.setNom(georisquePaginatedSisParcelle.getData().get(0).getNom());
            secteurInformationSolDTO.setFicheRisque(georisquePaginatedSisParcelle.getData().get(0).getFiche_risque());
    
            avisDTO.getSecteurInformationSolSurParcelleDTOs().add(secteurInformationSolDTO);
        }
        
        if (!georisquePaginatedSisRayonParcelle.getData().isEmpty()) {
            georisquePaginatedSisParcelle.getData().forEach(secteurInformationSols -> {
    
                avisDTO.getLeaflet().getSis().add(GeoJsonUtils.toGeoJson(secteurInformationSols.getGeom(),
                                                                         Stream.of(new AbstractMap.SimpleEntry<>("Nom", secteurInformationSols.getNom()))
                                                                                 .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))));
    
                SecteurInformationSolDTO secteurInformationSolDTO = new SecteurInformationSolDTO();
                secteurInformationSolDTO.setId(secteurInformationSols.getId_sis());
                secteurInformationSolDTO.setNom(secteurInformationSols.getNom());
                secteurInformationSolDTO.setFicheRisque(secteurInformationSols.getFiche_risque());
    
                avisDTO.getSecteurInformationSolRayonParcelleDTOs().add(secteurInformationSolDTO);
            });
        }
    }
    
    private void getAvisPPR(AvisDTO avisDTO, List<Geometry<?>> parcelleSitesSolsPolues, String codeINSEE) {
    
        String parcelleSitesSolsPoluesGeoJson = GeoJsonUtils.getGeometryFromGeoJson(GeoJsonUtils.toGeoJson(parcelleSitesSolsPolues));
    
        List<PlanPreventionRisquesGasparDTO> planPreventionRisquesList = new ArrayList<>();
    
        IGNCartoAssiettePaginatedFeatures assiettes = gestionIGNCartoFacade.rechercherAssiettesContenantPolygon(parcelleSitesSolsPoluesGeoJson);
    
        if (assiettes != null) {
            for (IGNCartoAssiettePaginatedFeatures.Assiette assiette : assiettes.getFeatures()) {
            
                IGNCartoGenerateurPaginatedFeatures generateurs = gestionIGNCartoFacade.rechercherGenerateurContenantPolygon(parcelleSitesSolsPoluesGeoJson, assiette.getProperties().getPartition());
            
                if (generateurs != null && !generateurs.getFeatures().isEmpty()) {
                
                    PlanPreventionRisquesGasparQO planPreventionRisquesGasparQO = new PlanPreventionRisquesGasparQO();
                    planPreventionRisquesGasparQO.setIdGaspar(generateurs.getFeatures().get(0).getProperties().getId_gaspar());
                    planPreventionRisquesGasparQO.setCodeINSEE(codeINSEE);
                
                    List<PlanPreventionRisquesGasparDTO> gaspars = gestionPlanPreventionRisquesGasparFacade.rechercherAvecCritere(planPreventionRisquesGasparQO);
                
                    if (gaspars.size() == 1) {
                    
                        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
                    
                        Map<String, Object> properties = Stream.of(new AbstractMap.SimpleEntry<>("'PPR'", gaspars.get(0).getAlea().getFamilleAlea().getLibelle()),
                                                                   new AbstractMap.SimpleEntry<>("approuvéLe", sdf.format(gaspars.get(0).getDateApprobation())))
                                                                 .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
                    
                        avisDTO.getLeaflet().getPpr().add(GeoJsonUtils.toGeoJson(assiette.getGeometry(), properties));
                        planPreventionRisquesList.add(gaspars.get(0));
                    }
                }
            }
        }
        
        avisDTO.setPlanPreventionRisquesDTOs(planPreventionRisquesList);
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
    
    private void getAvisICPE(AvisDTO avisDTO, List<Geometry<?>> parcelleSitesSolsPolues, Geometry<?> touchesParcelle, Geometry<?> expendedParcelle, String codeINSEE) {
        
        avisDTO.setInstallationClasseeSurParcelleDTOs((List<InstallationClasseeDTO>) removeLowPrecision(gestionInstallationClasseeFacade.rechercherInstallationsDansPolygons(parcelleSitesSolsPolues)));
        avisDTO.setInstallationClasseeProximiteParcelleDTOs((List<InstallationClasseeDTO>) removeLowPrecision(gestionInstallationClasseeFacade.rechercherSitesDansPolygon(touchesParcelle)));
        avisDTO.setInstallationClasseeRayonParcelleDTOs((List<InstallationClasseeDTO>) removeLowPrecision(gestionInstallationClasseeFacade.rechercherSitesDansPolygon(expendedParcelle)));
        
        avisDTO.getInstallationClasseeRayonParcelleDTOs().forEach(icpe -> avisDTO.getLeaflet().getIcpe().add(icpe.getEwkt()));
        
        avisDTO.getInstallationClasseeProximiteParcelleDTOs().removeAll(avisDTO.getInstallationClasseeSurParcelleDTOs());
        
        avisDTO.getInstallationClasseeRayonParcelleDTOs().removeAll(avisDTO.getInstallationClasseeSurParcelleDTOs());
        avisDTO.getInstallationClasseeRayonParcelleDTOs().removeAll(avisDTO.getInstallationClasseeProximiteParcelleDTOs());
        
        avisDTO.setInstallationClasseeNonGeorerenceesDTOs(gestionInstallationClasseeFacade.rechercherInstallationsAvecFaiblePrecisionDeGeolocalisation(codeINSEE));
    }
    
    private void getAvisBasol(AvisDTO avisDTO, List<Geometry<?>> parcelleSitesSolsPolues, Geometry<?> touchesParcelle, Geometry<?> expendedParcelle, String codeINSEE) {
        
        avisDTO.setSiteIndustrielBasolSurParcelleDTOs((List<SiteIndustrielBasolDTO>) removeLowPrecision(gestionSiteIndustrielBasolFacade.rechercherSitesDansPolygons(parcelleSitesSolsPolues)));
        avisDTO.setSiteIndustrielBasolProximiteParcelleDTOs((List<SiteIndustrielBasolDTO>) removeLowPrecision(gestionSiteIndustrielBasolFacade.rechercherSitesDansPolygon(touchesParcelle)));
        avisDTO.setSiteIndustrielBasolRayonParcelleDTOs((List<SiteIndustrielBasolDTO>) removeLowPrecision(gestionSiteIndustrielBasolFacade.rechercherSitesDansPolygon(expendedParcelle)));
        
        avisDTO.getSiteIndustrielBasolRayonParcelleDTOs().forEach(sib -> avisDTO.getLeaflet().getBasol().add(sib.getEwkt()));
        
        avisDTO.getSiteIndustrielBasolProximiteParcelleDTOs().removeAll(avisDTO.getSiteIndustrielBasolSurParcelleDTOs());
        
        avisDTO.getSiteIndustrielBasolRayonParcelleDTOs().removeAll(avisDTO.getSiteIndustrielBasolSurParcelleDTOs());
        avisDTO.getSiteIndustrielBasolRayonParcelleDTOs().removeAll(avisDTO.getSiteIndustrielBasolProximiteParcelleDTOs());
        
        avisDTO.setSiteIndustrielBasolNonGeorerenceesDTOs(gestionSiteIndustrielBasolFacade.rechercherSitesAvecFaiblePrecisionDeGeolocalisation(codeINSEE));
    }
    
    private void getAvisBasias(AvisDTO avisDTO, Geometry<?> parcelle, List<Geometry<?>> parcelleSitesSolsPolues, Geometry<?> touchesParcelle, Geometry<?> expendedParcelle, String nomProprietaire,
                               String codeINSEE) {
        
        avisDTO.setSiteIndustrielBasiasSurParcelleDTOs((List<SiteIndustrielBasiasDTO>) removeLowPrecision(gestionSiteIndustrielBasiasFacade.rechercherSitesDansPolygons(parcelleSitesSolsPolues)));
        avisDTO.setSiteIndustrielBasiasProximiteParcelleDTOs((List<SiteIndustrielBasiasDTO>) removeLowPrecision(gestionSiteIndustrielBasiasFacade.rechercherSitesDansPolygon(touchesParcelle)));
        avisDTO.setSiteIndustrielBasiasRayonParcelleDTOs((List<SiteIndustrielBasiasDTO>) removeLowPrecision(gestionSiteIndustrielBasiasFacade.rechercherSitesDansPolygon(expendedParcelle)));
        
        avisDTO.getSiteIndustrielBasiasRayonParcelleDTOs().forEach(sib -> avisDTO.getLeaflet().getBasias().add(sib.getEwkt()));
        
        if (!StringUtils.isEmpty(nomProprietaire)) {
            avisDTO.setSiteIndustrielBasiasParRaisonSocialeDTOs(gestionSiteIndustrielBasiasFacade.rechercherParNomProprietaireDansRayonGeometry(parcelle, nomProprietaire, 5000D));
        }
        
        avisDTO.getSiteIndustrielBasiasProximiteParcelleDTOs().removeAll(avisDTO.getSiteIndustrielBasiasSurParcelleDTOs());
        
        avisDTO.getSiteIndustrielBasiasRayonParcelleDTOs().removeAll(avisDTO.getSiteIndustrielBasiasSurParcelleDTOs());
        avisDTO.getSiteIndustrielBasiasRayonParcelleDTOs().removeAll(avisDTO.getSiteIndustrielBasiasProximiteParcelleDTOs());
        
        avisDTO.getSiteIndustrielBasiasParRaisonSocialeDTOs().removeAll(avisDTO.getSiteIndustrielBasiasSurParcelleDTOs());
        avisDTO.getSiteIndustrielBasiasParRaisonSocialeDTOs().removeAll(avisDTO.getSiteIndustrielBasiasProximiteParcelleDTOs());
        avisDTO.getSiteIndustrielBasiasParRaisonSocialeDTOs().removeAll(avisDTO.getSiteIndustrielBasiasRayonParcelleDTOs());
        
        avisDTO.setSiteIndustrielBasiasNonGeorerenceesDTOs(gestionSiteIndustrielBasiasFacade.rechercherSitesAvecFaiblePrecisionDeGeolocalisation(codeINSEE));
    }
    
    private List<? extends AbstractLocalisationAvecPrecision> removeLowPrecision(List<? extends AbstractLocalisationAvecPrecision> sites) {
        
        sites.removeIf(site -> !site.getPrecision().equals(PrecisionEnum.PARCELLE.getCode()) &&
                               !site.getPrecision().equals(PrecisionEnum.NUMERO.getCode()));
        
        return sites;
    }
}


