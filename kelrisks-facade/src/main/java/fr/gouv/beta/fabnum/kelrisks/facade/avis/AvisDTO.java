package fr.gouv.beta.fabnum.kelrisks.facade.avis;

import fr.gouv.beta.fabnum.commun.facade.dto.JsonInfoDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.InstallationClasseeDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteIndustrielBasiasDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteIndustrielBasolDTO;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class AvisDTO extends JsonInfoDTO {
    
    private String parcelle = "";
    private String idban    = "";
    
    private List<SiteIndustrielBasiasDTO> siteIndustrielBasiasSurParcelleDTOs       = new ArrayList<>();
    private List<SiteIndustrielBasiasDTO> siteIndustrielBasiasRayonParcelleDTOs     = new ArrayList<>();
    private List<SiteIndustrielBasiasDTO> siteIndustrielBasiasProximiteParcelleDTOs = new ArrayList<>();
    private List<SiteIndustrielBasiasDTO> siteIndustrielBasiasParRaisonSocialeDTOs  = new ArrayList<>();
    
    private List<SiteIndustrielBasolDTO> siteIndustrielBasolSurParcelleDTOs       = new ArrayList<>();
    private List<SiteIndustrielBasolDTO> siteIndustrielBasolRayonParcelleDTOs     = new ArrayList<>();
    private List<SiteIndustrielBasolDTO> siteIndustrielBasolProximiteParcelleDTOs = new ArrayList<>();
    
    private List<InstallationClasseeDTO> installationClasseeSurParcelleDTOs       = new ArrayList<>();
    private List<InstallationClasseeDTO> installationClasseeRayonParcelleDTOs     = new ArrayList<>();
    private List<InstallationClasseeDTO> installationClasseeProximiteParcelleDTOs = new ArrayList<>();
    private List<InstallationClasseeDTO> installationClasseeNonGeorerenceesDTOs   = new ArrayList<>();
}
