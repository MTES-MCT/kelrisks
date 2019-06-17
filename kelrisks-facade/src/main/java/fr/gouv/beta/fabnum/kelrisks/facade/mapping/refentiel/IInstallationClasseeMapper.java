package fr.gouv.beta.fabnum.kelrisks.facade.mapping.refentiel;

import fr.gouv.beta.fabnum.commun.facade.dto.AutocompleteDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.InstallationClasseeDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.ICommonMapperConfig;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.InstallationClassee;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(config = ICommonMapperConfig.class)
public interface IInstallationClasseeMapper extends IGeometryMapper {
    
    List<InstallationClasseeDTO> toDTOs(List<InstallationClassee> installationClassees);
    
    @Mappings(
            @Mapping(target = "ewkt", source = "point", qualifiedByName = "toWKT")
    )
    InstallationClasseeDTO toDTO(InstallationClassee installationClassee);
    
    List<AutocompleteDTO> toAutocompleteDTOs(List<InstallationClassee> installationClassees);
    
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "raisonSociale", target = "code"),
            @Mapping(source = "raisonSociale", target = "libelle")
    })
    AutocompleteDTO toAutocompleteDTO(InstallationClassee installationClassee);
}