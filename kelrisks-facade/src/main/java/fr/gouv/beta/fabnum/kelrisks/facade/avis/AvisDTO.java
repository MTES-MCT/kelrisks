package fr.gouv.beta.fabnum.kelrisks.facade.avis;

import fr.gouv.beta.fabnum.commun.facade.dto.JsonInfoDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.ArgileDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.CommuneDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.InstallationClasseeDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.InstallationNucleaireDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.PlanExpositionBruitDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.PlanPreventionRisquesGasparDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SecteurInformationSolDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteIndustrielBasiasDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteIndustrielBasolDTO;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedAZI;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedTRI;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.geolatte.geom.Geometry;

@Data
public class AvisDTO extends JsonInfoDTO {
    
    private Leaflet leaflet = new Leaflet();
    private Summary summary = new Summary();
    
    private List<SiteIndustrielBasiasDTO> siteIndustrielBasiasSurParcelleDTOs       = new ArrayList<>();
    private List<SiteIndustrielBasiasDTO> siteIndustrielBasiasRayonParcelleDTOs     = new ArrayList<>();
    private List<SiteIndustrielBasiasDTO> siteIndustrielBasiasProximiteParcelleDTOs = new ArrayList<>();
    private List<SiteIndustrielBasiasDTO> siteIndustrielBasiasParRaisonSocialeDTOs  = new ArrayList<>();
    private List<SiteIndustrielBasiasDTO> siteIndustrielBasiasNonGeorerenceesDTOs   = new ArrayList<>();
    
    private List<SiteIndustrielBasolDTO> siteIndustrielBasolSurParcelleDTOs       = new ArrayList<>();
    private List<SiteIndustrielBasolDTO> siteIndustrielBasolRayonParcelleDTOs     = new ArrayList<>();
    private List<SiteIndustrielBasolDTO> siteIndustrielBasolProximiteParcelleDTOs = new ArrayList<>();
    private List<SiteIndustrielBasolDTO> siteIndustrielBasolNonGeorerenceesDTOs   = new ArrayList<>();
    
    private List<SecteurInformationSolDTO> secteurInformationSolSurParcelleDTOs       = new ArrayList<>();
    private List<SecteurInformationSolDTO> secteurInformationSolRayonParcelleDTOs     = new ArrayList<>();
    private List<SecteurInformationSolDTO> secteurInformationSolProximiteParcelleDTOs = new ArrayList<>();
    private List<SecteurInformationSolDTO> secteurInformationSolNonGeorerenceesDTOs   = new ArrayList<>();
    
    private List<InstallationClasseeDTO> installationClasseeSurParcelleDTOs       = new ArrayList<>();
    private List<InstallationClasseeDTO> installationClasseeRayonParcelleDTOs     = new ArrayList<>();
    private List<InstallationClasseeDTO> installationClasseeProximiteParcelleDTOs = new ArrayList<>();
    private List<InstallationClasseeDTO> installationClasseeNonGeorerenceesDTOs   = new ArrayList<>();
    
    private List<ArgileDTO> lentillesArgile = new ArrayList<>();
    private int             niveauArgile;
    
    private List<PlanPreventionRisquesGasparDTO> planPreventionRisquesDTOs;
    
    private List<GeorisquePaginatedTRI.TRI> TRIs = new ArrayList<>();
    private List<GeorisquePaginatedAZI.AZI> AZIs = new ArrayList<>();
    
    private List<Geometry<?>>              geogCanalisations         = new ArrayList<>();
    private List<InstallationNucleaireDTO> installationNucleaireDTOS = new ArrayList<>();
    private boolean                        hasCentraleNucleaire      = false;
    private String                         zonePlanExpositionBruit;
    private List<PlanExpositionBruitDTO>   plansExpositionBruit;
    
    public String toString() {
    
        String out = "";
    
        out += siteIndustrielBasiasSurParcelleDTOs.size();
        out += "==" + siteIndustrielBasiasSurParcelleDTOs.stream().map(SiteIndustrielBasiasDTO::getIdentifiant).collect(Collectors.joining(","));
        //        out += "||" + siteIndustrielBasiasRayonParcelleDTOs.size();
        //        out += "==" + siteIndustrielBasiasRayonParcelleDTOs.stream().map(SiteIndustrielBasiasDTO::getIdentifiant).collect(Collectors.joining(","));
        out += "||" + siteIndustrielBasiasProximiteParcelleDTOs.size();
        out += "==" + siteIndustrielBasiasProximiteParcelleDTOs.stream().map(SiteIndustrielBasiasDTO::getIdentifiant).collect(Collectors.joining(","));
        //        out += "||" + siteIndustrielBasiasNonGeorerenceesDTOs.size();
        //        out += "==" + siteIndustrielBasiasNonGeorerenceesDTOs.stream().map(SiteIndustrielBasiasDTO::getIdentifiant).collect(Collectors.joining(","));
        out += "||" + siteIndustrielBasolSurParcelleDTOs.size();
        out += "==" + siteIndustrielBasolSurParcelleDTOs.stream().map(SiteIndustrielBasolDTO::getNumerobasol).collect(Collectors.joining(","));
        //        out += "||" + siteIndustrielBasolRayonParcelleDTOs.size();
        //        out += "==" + siteIndustrielBasolRayonParcelleDTOs.stream().map(SiteIndustrielBasolDTO::getNumerobasol).collect(Collectors.joining(","));
        out += "||" + siteIndustrielBasolProximiteParcelleDTOs.size();
        out += "==" + siteIndustrielBasolProximiteParcelleDTOs.stream().map(SiteIndustrielBasolDTO::getNumerobasol).collect(Collectors.joining(","));
        //        out += "||" + siteIndustrielBasolNonGeorerenceesDTOs.size();
        //        out += "==" + siteIndustrielBasolNonGeorerenceesDTOs.stream().map(SiteIndustrielBasolDTO::getNumerobasol).collect(Collectors.joining(","));
        out += "||" + secteurInformationSolSurParcelleDTOs.size();
        out += "==" + secteurInformationSolSurParcelleDTOs.stream().map(SecteurInformationSolDTO::getNom).collect(Collectors.joining(","));
        //        out += "||" + secteurInformationSolRayonParcelleDTOs.size();
        //        out += "==" + secteurInformationSolRayonParcelleDTOs.stream().map(SecteurInformationSolDTO::getNom).collect(Collectors.joining(","));
        out += "||" + secteurInformationSolProximiteParcelleDTOs.size();
        out += "==" + secteurInformationSolProximiteParcelleDTOs.stream().map(SecteurInformationSolDTO::getNom).collect(Collectors.joining(","));
        //        out += "||" + secteurInformationSolNonGeorerenceesDTOs.size();
        //        out += "==" + secteurInformationSolNonGeorerenceesDTOs.stream().map(SecteurInformationSolDTO::getNom).collect(Collectors.joining(","));
        out += "||" + installationClasseeSurParcelleDTOs.size();
        out += "==" + installationClasseeSurParcelleDTOs.stream().map(InstallationClasseeDTO::getNom).collect(Collectors.joining(","));
        //        out += "||" + installationClasseeRayonParcelleDTOs.size();
        //        out += "==" + installationClasseeRayonParcelleDTOs.stream().map(InstallationClasseeDTO::getNom).collect(Collectors.joining(","));
        out += "||" + installationClasseeProximiteParcelleDTOs.size();
        out += "==" + installationClasseeProximiteParcelleDTOs.stream().map(InstallationClasseeDTO::getNom).collect(Collectors.joining(","));
        //        out += "||" + installationClasseeNonGeorerenceesDTOs.size();
        //        out += "==" + installationClasseeNonGeorerenceesDTOs.stream().map(InstallationClasseeDTO::getNom).collect(Collectors.joining(","));
        out += "||" + niveauArgile;
        out += "||" + planPreventionRisquesDTOs.size();
        out += "==" + planPreventionRisquesDTOs.stream().map(PlanPreventionRisquesGasparDTO::getIdGaspar).collect(Collectors.joining(","));
        out += "||" + TRIs.size();
        out += "||" + AZIs.size();
        out += "||" + geogCanalisations.size();
        out += "||" + installationNucleaireDTOS.size();
        out += "==" + installationNucleaireDTOS.stream().map(InstallationNucleaireDTO::getNomInstallation).collect(Collectors.joining(","));
        out += "||" + zonePlanExpositionBruit;
        
        return out;
    }
    
    @Data
    public static class Summary {
        
        private String codeUrl = "";
        
        private String     adresse;
        private CommuneDTO commune;
        
        private String codeParcelle = "";
    }
    
    @Data
    public static class Leaflet {
    
        private Point        center    = new Point();
        private List<String> parcelles = new ArrayList<>();
        private String       adresse   = "";
        private List<String> basias    = new ArrayList<>();
        private List<String> basol     = new ArrayList<>();
        private List<String> sis       = new ArrayList<>();
        private List<String> icpe      = new ArrayList<>();
        private List<String> ssp       = new ArrayList<>();
    
        @Data
        public static class Point {
        
            String x = "";
            String y = "";
        
            public Point() {
            
            }
            
            public Point(String x, String y) {
                
                this.x = x;
                this.y = y;
            }
        }
    }
}