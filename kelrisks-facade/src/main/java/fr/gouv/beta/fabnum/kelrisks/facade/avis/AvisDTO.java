package fr.gouv.beta.fabnum.kelrisks.facade.avis;

import fr.gouv.beta.fabnum.commun.facade.dto.JsonInfoDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.CommuneDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.InstallationClasseeDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SecteurInformationSolDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteIndustrielBasiasDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteIndustrielBasolDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AvisDTO extends JsonInfoDTO {
    
    private Leaflet leaflet = new Leaflet();
    private Summary summary = new Summary();
    
    private List<SiteIndustrielBasiasDTO> siteIndustrielBasiasSurParcelleDTOs       = new ArrayList<>();
    private List<SiteIndustrielBasiasDTO> siteIndustrielBasiasRayonParcelleDTOs     = new ArrayList<>();
    private List<SiteIndustrielBasiasDTO> siteIndustrielBasiasProximiteParcelleDTOs = new ArrayList<>();
    private List<SiteIndustrielBasiasDTO> siteIndustrielBasiasParRaisonSocialeDTOs  = new ArrayList<>();
    
    private List<SiteIndustrielBasolDTO> siteIndustrielBasolSurParcelleDTOs       = new ArrayList<>();
    private List<SiteIndustrielBasolDTO> siteIndustrielBasolRayonParcelleDTOs     = new ArrayList<>();
    private List<SiteIndustrielBasolDTO> siteIndustrielBasolProximiteParcelleDTOs = new ArrayList<>();
    
    private List<SecteurInformationSolDTO> secteurInformationSolSurParcelleDTOs       = new ArrayList<>();
    private List<SecteurInformationSolDTO> secteurInformationSolRayonParcelleDTOs     = new ArrayList<>();
    private List<SecteurInformationSolDTO> secteurInformationSolProximiteParcelleDTOs = new ArrayList<>();
    
    private List<InstallationClasseeDTO> installationClasseeSurParcelleDTOs       = new ArrayList<>();
    private List<InstallationClasseeDTO> installationClasseeRayonParcelleDTOs     = new ArrayList<>();
    private List<InstallationClasseeDTO> installationClasseeProximiteParcelleDTOs = new ArrayList<>();
    private List<InstallationClasseeDTO> installationClasseeNonGeorerenceesDTOs   = new ArrayList<>();
    
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
