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
    
    private List<SiteIndustrielBasiasDTO> siteIndustrielBasiasSurParcelleDTOs      = new ArrayList<>();
    private List<SiteIndustrielBasiasDTO> siteIndustrielBasiasAutourParcelleDTOs   = new ArrayList<>();
    private List<SiteIndustrielBasiasDTO> siteIndustrielBasiasParRaisonSocialeDTOs = new ArrayList<>();
    private List<SiteIndustrielBasolDTO>  siteIndustrielBasolSurParcelleDTOs       = new ArrayList<>();
    private List<SiteIndustrielBasolDTO>  siteIndustrielBasolAutourParcelleDTOs    = new ArrayList<>();
    private List<InstallationClasseeDTO>  installationClasseeSurParcelleDTOs       = new ArrayList<>();
    private List<InstallationClasseeDTO>  installationClasseeAutourParcelleDTOs    = new ArrayList<>();
    private List<InstallationClasseeDTO>  installationClasseeNonGeorerenceesDTOs   = new ArrayList<>();
}
