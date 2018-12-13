package fr.gouv.beta.fabnum.kelrisks.facade.mapping.refentiel;


import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.InstallationClasseeDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.ICommonMapperConfig;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.InstallationClassee;

import java.util.List;

import org.mapstruct.Mapper;


@Mapper(config = ICommonMapperConfig.class)
public interface IInstallationClasseeMapper {
    
    List<InstallationClasseeDTO> toDTOs(List<InstallationClassee> installationClassees);
    
    InstallationClasseeDTO toDTO(InstallationClassee installationClassee);
}