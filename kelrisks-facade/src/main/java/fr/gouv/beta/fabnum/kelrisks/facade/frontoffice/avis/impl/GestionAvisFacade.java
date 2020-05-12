package fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.avis.impl;

import fr.gouv.beta.fabnum.commun.facade.AbstractFacade;
import fr.gouv.beta.fabnum.commun.utils.GeoJsonUtils;
import fr.gouv.beta.fabnum.kelrisks.facade.avis.AvisDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.AbstractLocalisationAvecPrecision;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.ArgileDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.CommuneDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.InstallationClasseeDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.InstallationNucleaireDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.ParcelleDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.PlanPreventionRisquesGasparDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SecteurInformationSolDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteIndustrielBasiasDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteIndustrielBasolDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteSolPolueDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.avis.IGestionAvisFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionArgileFacade;
import fr.gouv.beta.fabnum.kelrisks.facade.frontoffice.referentiel.IGestionBRGMFacade;
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
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.BRGMPaginatedCanalisation;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.BRGMPaginatedInstallationNuclaire;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedAZI;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedPPR;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedRadon;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedSIS;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedSismique;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedTRI;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.IGNCartoAssiettePaginatedFeatures;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.IGNCartoGenerateurPaginatedFeatures;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.enums.PrecisionEnum;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.qo.PlanPreventionRisquesGasparQO;

import java.text.SimpleDateFormat;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.validation.constraints.NotNull;

import org.geolatte.geom.Geometry;
import org.geolatte.geom.Point;
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
    IGestionArgileFacade                      gestionArgileFacade;
    @Autowired
    IGestionGeorisquesFacade                  gestionGeorisquesFacade;
    @Autowired
    IGestionGeoDataGouvFacade                 gestionGeoDataGouvFacade;
    @Autowired
    IGestionIGNCartoFacade                    gestionIGNCartoFacade;
    @Autowired
    IGestionPlanPreventionRisquesGasparFacade gestionPlanPreventionRisquesGasparFacade;
    @Autowired
    IGestionBRGMFacade                        gestionBRGMFacade;
    
    @Override
    public AvisDTO rendreAvis(List<ParcelleDTO> parcelleDTOs, CommuneDTO communeDTO, @NotNull String nomAdresse) {
        
        AvisDTO avisDTO = new AvisDTO();
        
        avisDTO.getSummary().setCommune(communeDTO);
        avisDTO.getSummary().setAdresse(nomAdresse);
        
        return getAvisFromParcelle(avisDTO, parcelleDTOs, communeDTO);
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
    
        return getAvisFromParcelle(avisDTO, Collections.singletonList(parcelleDTO), communeDTO);
    }
    
    private AvisDTO getAvisFromParcelle(AvisDTO avisDTO, List<ParcelleDTO> parcelleDTOs, CommuneDTO communeDTO) {
        
        long startTime = System.currentTimeMillis();
        Geometry<?> parcellesUnion = gestionParcelleFacade.rechercherUnionParcelles(parcelleDTOs.stream()
                                                                                            .map(ParcelleDTO::getId)
                                                                                            .collect(Collectors.toList()));
        //        System.out.println((System.currentTimeMillis() - startTime) + " => " + "gestionParcelleFacade.rechercherUnionParcelles");
        Geometry<?> touchesParcelle = gestionParcelleFacade.rechercherUnionParcellesContigues(parcellesUnion);
        //        System.out.println((System.currentTimeMillis() - startTime) + " => " + "gestionParcelleFacade.rechercherUnionParcellesContigues");
        Geometry<?> expendedParcelle = gestionParcelleFacade.rechercherExpendedParcelle(parcellesUnion, 500);
        //        System.out.println((System.currentTimeMillis() - startTime) + " => " + "gestionParcelleFacade.rechercherExpendedParcelle");
        Point<?> centroid = (Point<?>) gestionParcelleFacade.rechercherCentroidParcelle(parcellesUnion);
        //        System.out.println((System.currentTimeMillis() - startTime) + " => " + "gestionParcelleFacade.rechercherCentroidParcelle");
        
        avisDTO.getLeaflet().setCenter(new AvisDTO.Leaflet.Point(Double.toString(centroid.getPositionN(0).getCoordinate(CoordinateSystemAxis.mkLonAxis())),
                                                                 Double.toString(centroid.getPositionN(0).getCoordinate(CoordinateSystemAxis.mkLatAxis()))));
        
        avisDTO.getSummary().setCodeParcelle(parcelleDTOs.stream()
                                                     .map(parcelleDTO -> parcelleDTO.getSection() + "-" + parcelleDTO.getNumero())
                                                     .collect(Collectors.joining(", ")));
        avisDTO.getLeaflet().setParcelles(parcelleDTOs.stream()
                                                  .map(parcelleDTO -> GeoJsonUtils.toGeoJson(parcelleDTO.getMultiPolygon(),
                                                                                             Stream.of(new SimpleEntry<>("parcelle", parcelleDTO.getSection() + "-" + parcelleDTO.getNumero()))
                                                                                                     .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))))
                                                  .collect(Collectors.toList()));
        
        // Recherche d'une éventuelle zone poluée contenant la parcelle
        List<Geometry<?>>     parcelleSitesSolsPolues = new ArrayList<>();
        List<SiteSolPolueDTO> siteSolPolueDTOs        = gestionSiteSolPolueFacade.rechercherZoneContenantParcelle(parcellesUnion);
        if (!siteSolPolueDTOs.isEmpty()) {
            siteSolPolueDTOs.forEach(siteSolPolueDTO -> {
                parcelleSitesSolsPolues.add(siteSolPolueDTO.getMultiPolygon());
                avisDTO.getLeaflet().getSsp().add(siteSolPolueDTO.getEwkt());
            });
        }
        else { parcelleSitesSolsPolues.add(parcellesUnion); }
        //        System.out.println((System.currentTimeMillis() - startTime) + " => " + "gestionSiteSolPolueFacade.rechercherZoneContenantParcelle");
        
        getAvisBasias(avisDTO, parcellesUnion, parcelleSitesSolsPolues, touchesParcelle, expendedParcelle, communeDTO.getCodeINSEE());
        //        System.out.println((System.currentTimeMillis() - startTime) + " => " + "getAvisBasias");
        
        getAvisBasol(avisDTO, parcelleSitesSolsPolues, touchesParcelle, expendedParcelle, communeDTO.getCodeINSEE());
        //        System.out.println((System.currentTimeMillis() - startTime) + " => " + "getAvisBasol");
        
        getAvisICPE(avisDTO, parcelleSitesSolsPolues, touchesParcelle, expendedParcelle, communeDTO.getCodeINSEE());
        //        System.out.println((System.currentTimeMillis() - startTime) + " => " + "getAvisICPE");
        
        getAvisSis(avisDTO, centroid);
        //        System.out.println((System.currentTimeMillis() - startTime) + " => " + "getAvisSis");
        
        getAvisPPR(avisDTO, parcelleSitesSolsPolues, centroid, communeDTO.getCodeINSEE());
        //        System.out.println((System.currentTimeMillis() - startTime) + " => " + "getAvisPPR");
        
        getAvisAZI(avisDTO, communeDTO.getCodeINSEE());
        //        System.out.println((System.currentTimeMillis() - startTime) + " => " + "getAvisAZI");
        
        getAvisTRI(avisDTO, communeDTO.getCodeINSEE());
        //        System.out.println((System.currentTimeMillis() - startTime) + " => " + "getAvisTRI");
        
        getAvisArgile(avisDTO, parcellesUnion);
        //        System.out.println((System.currentTimeMillis() - startTime) + " => " + "getAvisArgile");
        
        getAvisSismicite(avisDTO, communeDTO.getCodeINSEE());
        //        System.out.println((System.currentTimeMillis() - startTime) + " => " + "getAvisSismicite");
        
        getAvisRadon(avisDTO, communeDTO.getCodeINSEE());
        //        System.out.println((System.currentTimeMillis() - startTime) + " => " + "getAvisRadon");
        
        getAvisCanalisations(avisDTO, centroid);
        //        System.out.println((System.currentTimeMillis() - startTime) + " => " + "getAvisCanalisations");
        
        getAvisInstallationsNucleaires(avisDTO, centroid);
        //        System.out.println((System.currentTimeMillis() - startTime) + " => " + "getAvisInstallationsNucleaires");
        
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
                                                                         Stream.of(new SimpleEntry<>("Nom", secteurInformationSols.getNom()))
                                                                                 .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))));
    
                SecteurInformationSolDTO secteurInformationSolDTO = new SecteurInformationSolDTO();
                secteurInformationSolDTO.setId(secteurInformationSols.getId_sis());
                secteurInformationSolDTO.setNom(secteurInformationSols.getNom());
                secteurInformationSolDTO.setFicheRisque(secteurInformationSols.getFiche_risque());
    
                avisDTO.getSecteurInformationSolRayonParcelleDTOs().add(secteurInformationSolDTO);
            });
        }
    }
    
    private void getAvisPPR(AvisDTO avisDTO, List<Geometry<?>> parcelleSitesSolsPolues, Point<?> centroid, String codeINSEE) {
        
        String parcelleSitesSolsPoluesGeoJson = GeoJsonUtils.getGeometryFromGeoJson(GeoJsonUtils.toGeoJson(parcelleSitesSolsPolues));
        
        List<PlanPreventionRisquesGasparDTO> planPreventionRisquesList = new ArrayList<>();
        
        IGNCartoAssiettePaginatedFeatures assiettes = gestionIGNCartoFacade.rechercherAssiettesContenantPolygon(parcelleSitesSolsPoluesGeoJson);
        
        if (assiettes != null) {
    
            assiettes.getFeatures().forEach(assiette -> {
                
                IGNCartoGenerateurPaginatedFeatures generateurs = gestionIGNCartoFacade.rechercherGenerateurContenantPolygon(parcelleSitesSolsPoluesGeoJson, assiette.getProperties().getPartition());
        
                if (generateurs != null) {
            
                    generateurs.getFeatures().stream()
                            .filter(generateur -> generateur.getProperties().getIdgen().equals(assiette.getProperties().getIdgen())) // Sécurisation de la jointure assiette / générateur qui ne peut
                            // être faite via l'API GpU
                            .findFirst()
                            .ifPresent(generateur -> {
                                PlanPreventionRisquesGasparDTO gaspar = getGaspar(codeINSEE, generateur.getProperties().getId_gaspar(), assiette.getGeometry());
    
                                if (gaspar != null) {
                                    planPreventionRisquesList.add(gaspar);
                                }
                            });
                }
            });
        }
        
        GeorisquePaginatedPPR georisquePaginatedPPR = gestionGeorisquesFacade.rechercherPprCoordonnees(centroid.getPositionN(0).getCoordinate(CoordinateSystemAxis.mkLonAxis()),
                                                                                                       centroid.getPositionN(0).getCoordinate(CoordinateSystemAxis.mkLatAxis()));
        if (georisquePaginatedPPR != null) {
    
            georisquePaginatedPPR.getData().stream()
                    .filter(ppr -> planPreventionRisquesList.stream().noneMatch(pprGasparDTO -> pprGasparDTO.getIdGaspar().equals(ppr.getId_gaspar()))) // Éviter les doublons en raison de la
                    // multiplicité des sources
                    .forEach(ppr -> {
    
                        PlanPreventionRisquesGasparDTO gaspar = getGaspar(codeINSEE, ppr.getId_gaspar(), ppr.getGeom_perimetre());
    
                        if (gaspar != null) {
                            planPreventionRisquesList.add(gaspar);
                        }
                    });
        }
    
        avisDTO.setPlanPreventionRisquesDTOs(planPreventionRisquesList);
    }
    
    private void getAvisCanalisations(AvisDTO avisDTO, Point<?> centroid) {
    
        BRGMPaginatedCanalisation brgmPaginatedCanalisation =
                gestionBRGMFacade.rechercherCanalisationsCoordonnees(String.valueOf(centroid.getPositionN(0).getCoordinate(CoordinateSystemAxis.mkLonAxis())),
                                                                     String.valueOf(centroid.getPositionN(0).getCoordinate(CoordinateSystemAxis.mkLatAxis())),
                                                                     500);
    
        if (brgmPaginatedCanalisation != null) {
            brgmPaginatedCanalisation.getFeatures().forEach(canalisation -> avisDTO.getGeogCanalisations().add(canalisation.getGeometry()));
        }
    }
    
    private void getAvisInstallationsNucleaires(AvisDTO avisDTO, Point<?> centroid) {
    
        BRGMPaginatedInstallationNuclaire brgmPaginatedInstallationNuclaire =
                gestionBRGMFacade.rechercherInstallationsNucleairesCoordonnees(String.valueOf(centroid.getPositionN(0).getCoordinate(CoordinateSystemAxis.mkLonAxis())),
                                                                               String.valueOf(centroid.getPositionN(0).getCoordinate(CoordinateSystemAxis.mkLatAxis())),
                                                                               20000);
    
        List<InstallationNucleaireDTO> installationNucleaireDTOS = new ArrayList<>();
    
        if (brgmPaginatedInstallationNuclaire != null) {
            brgmPaginatedInstallationNuclaire.getFeatures().forEach(installationNucleaire -> {
    
                InstallationNucleaireDTO installationNucleaireDTO = new InstallationNucleaireDTO();
                installationNucleaireDTO.setNomInstallation(installationNucleaire.getProperties().getNom_inst());
                installationNucleaireDTO.setLibCommune(installationNucleaire.getProperties().getNom_com());
                installationNucleaireDTOS.add(installationNucleaireDTO);
            });
        }
    
        avisDTO.setInstallationNucleaireDTOS(installationNucleaireDTOS);
    }
    
    private PlanPreventionRisquesGasparDTO getGaspar(String codeINSEE, String idGaspar, Geometry<?> geometry) {
        
        PlanPreventionRisquesGasparQO planPreventionRisquesGasparQO = new PlanPreventionRisquesGasparQO();
        planPreventionRisquesGasparQO.setIdGaspar(idGaspar);
        planPreventionRisquesGasparQO.setCodeINSEE(codeINSEE);
        
        List<PlanPreventionRisquesGasparDTO> gaspars = gestionPlanPreventionRisquesGasparFacade.rechercherAvecCritere(planPreventionRisquesGasparQO);
        
        if (gaspars.size() == 1) {
            
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE);
            
            Map<String, Object> properties = Stream.of(new SimpleEntry<>("'PPR'", gaspars.get(0).getAlea().getFamilleAlea().getLibelle()),
                                                       new SimpleEntry<>("approuvéLe", sdf.format(gaspars.get(0).getDateApprobation())))
                                                     .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
            
            PlanPreventionRisquesGasparDTO planPreventionRisquesGasparDTO = gaspars.get(0);
            planPreventionRisquesGasparDTO.setAssiette(GeoJsonUtils.toGeoJson(geometry, properties));
            
            return planPreventionRisquesGasparDTO;
        }
        
        return null;
    }
    
    private void getAvisSismicite(AvisDTO avisDTO, String codeINSEE) {
        
        GeorisquePaginatedSismique georisquePaginatedSismique = gestionGeorisquesFacade.rechercherSismiciteCommune(codeINSEE);
        
        if (!georisquePaginatedSismique.getData().isEmpty()) {
            avisDTO.setCodeZoneSismicite(Integer.parseInt(georisquePaginatedSismique.getData().get(0).getCode_zone()));
        }
    }
    
    private void getAvisArgile(AvisDTO avisDTO, Geometry<?> parcelle) {
        
        List<ArgileDTO> argileDTOs = gestionArgileFacade.rechercherLentillesDansPolygon(parcelle);
        
        for (ArgileDTO argileDTO : argileDTOs) {
            if (avisDTO.getLentillesArgile() == null ||
                avisDTO.getLentillesArgile().getNiveauAlea() < argileDTO.getNiveauAlea()) {
                
                avisDTO.setLentillesArgile(argileDTO);
            }
        }
    }
    
    private void getAvisRadon(AvisDTO avisDTO, String codeINSEE) {
        
        GeorisquePaginatedRadon georisquePaginatedRadon = gestionGeorisquesFacade.rechercherRadonCommune(codeINSEE);
        
        if (!georisquePaginatedRadon.getData().isEmpty()) {
            avisDTO.setClassePotentielRadon(Integer.parseInt(georisquePaginatedRadon.getData().get(0).getClasse_potentiel()));
        }
    }
    
    private void getAvisTRI(AvisDTO avisDTO, String codeINSEE) {
        
        GeorisquePaginatedTRI georisquePaginatedRadon = gestionGeorisquesFacade.rechercherTRICommune(codeINSEE);
        
        if (!georisquePaginatedRadon.getData().isEmpty()) {
            avisDTO.setTRIs(georisquePaginatedRadon.getData());
        }
    }
    
    private void getAvisAZI(AvisDTO avisDTO, String codeINSEE) {
        
        GeorisquePaginatedAZI georisquePaginatedRadon = gestionGeorisquesFacade.rechercherAZICommune(codeINSEE);
        
        if (!georisquePaginatedRadon.getData().isEmpty()) {
            avisDTO.setAZIs(georisquePaginatedRadon.getData());
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
    
    private void getAvisBasias(AvisDTO avisDTO, Geometry<?> parcelle, List<Geometry<?>> parcelleSitesSolsPolues, Geometry<?> touchesParcelle, Geometry<?> expendedParcelle,
                               String codeINSEE) {
        
        avisDTO.setSiteIndustrielBasiasSurParcelleDTOs((List<SiteIndustrielBasiasDTO>) removeLowPrecision(gestionSiteIndustrielBasiasFacade.rechercherSitesDansPolygons(parcelleSitesSolsPolues)));
        avisDTO.setSiteIndustrielBasiasProximiteParcelleDTOs((List<SiteIndustrielBasiasDTO>) removeLowPrecision(gestionSiteIndustrielBasiasFacade.rechercherSitesDansPolygon(touchesParcelle)));
        avisDTO.setSiteIndustrielBasiasRayonParcelleDTOs((List<SiteIndustrielBasiasDTO>) removeLowPrecision(gestionSiteIndustrielBasiasFacade.rechercherSitesDansPolygon(expendedParcelle)));
        
        avisDTO.getSiteIndustrielBasiasRayonParcelleDTOs().forEach(sib -> avisDTO.getLeaflet().getBasias().add(sib.getEwkt()));
        
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


