package fr.gouv.beta.fabnum.kelrisks.facade.avis;

import fr.gouv.beta.fabnum.commun.facade.dto.JsonInfoDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.ArgileDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.CommuneDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.InstallationClasseeDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.InstallationNucleaireDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.PlanPreventionRisquesGasparDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SecteurInformationSolDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteIndustrielBasiasDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteIndustrielBasolDTO;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedAZI;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeorisquePaginatedTRI;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

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
    
    private ArgileDTO lentillesArgile;
    
    private List<PlanPreventionRisquesGasparDTO> planPreventionRisquesDTOs;
    
    private int codeZoneSismicite;
    private int classePotentielRadon;
    
    private List<GeorisquePaginatedTRI.TRI> TRIs = new ArrayList<>();
    private List<GeorisquePaginatedAZI.AZI> AZIs = new ArrayList<>();
    
    private List<Geometry<?>>              geogCanalisations         = new ArrayList<>();
    private List<InstallationNucleaireDTO> installationNucleaireDTOS = new ArrayList<>();
    
    @Data
    public static class Summary {
        
        private String codeUrl = "";
        
        private String     adresse;
        private CommuneDTO commune;
        
        private String codeParcelle = "";
        
        private String nomProprietaire = "";
    }
    
    @Data
    public static class Leaflet {
    
        private Point        center   = new Point();
        private String       parcelle = "";
        private String       adresse  = "";
        private List<String> basias   = new ArrayList<>();
        private List<String> basol    = new ArrayList<>();
        private List<String> sis      = new ArrayList<>();
        private List<String> icpe     = new ArrayList<>();
        private List<String> ssp      = new ArrayList<>();
        
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
