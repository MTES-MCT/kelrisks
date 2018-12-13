package fr.gouv.beta.fabnum.kelrisks.facade.avis;

import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.InstallationClasseeDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteIndustrielBasiasDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteIndustrielBasolDTO;
import lombok.Data;

import java.util.List;

@Data
public class AvisDTO {
    
    private List<SiteIndustrielBasiasDTO> siteIndustrielBasiasSurParcelleDTOs;
    private List<SiteIndustrielBasiasDTO> siteIndustrielBasiasAutourParcelleDTOs;
    private List<SiteIndustrielBasiasDTO> siteIndustrielBasiasParRaisonSocialeDTOs;
    private List<SiteIndustrielBasolDTO>  siteIndustrielBasolSurParcelleDTOs;
    private List<SiteIndustrielBasolDTO>  siteIndustrielBasolAutourParcelleDTOs;
    private List<InstallationClasseeDTO>  installationClasseeSurParcelleDTOs;
    private List<InstallationClasseeDTO>  installationClasseeAutourParcelleDTOs;
    private List<InstallationClasseeDTO>  installationClasseeNonGeorerenceesDTOs;
}
