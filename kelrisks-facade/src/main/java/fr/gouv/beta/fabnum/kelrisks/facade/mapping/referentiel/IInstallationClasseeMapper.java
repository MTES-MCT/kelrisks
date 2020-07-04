package fr.gouv.beta.fabnum.kelrisks.facade.mapping.referentiel;

import fr.gouv.beta.fabnum.commun.facade.dto.AutocompleteDTO;
import fr.gouv.beta.fabnum.commun.utils.GeoJsonUtils;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.InstallationClasseeDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.ICommonMapperConfig;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.InstallationClassee;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(config = ICommonMapperConfig.class,
        imports = {GeoJsonUtils.class, Map.class, Collectors.class, Stream.class, AbstractMap.class, StringUtils.class})
public interface IInstallationClasseeMapper extends IGeometryMapper {
    
    List<InstallationClasseeDTO> toDTOs(List<InstallationClassee> installationClassees);
    
    //    @formatter:off
    @Mappings(
            @Mapping(target = "ewkt", expression = "java(GeoJsonUtils.toGeoJson(installationClassee.getMultiPolygon(), Stream.of(new AbstractMap.SimpleEntry<>(\"nom\", StringUtils.stripToEmpty(installationClassee.getNom())), new AbstractMap.SimpleEntry<>(\"code\", StringUtils.stripToEmpty(installationClassee.getCode()))).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))))")
    )
    //    @formatter:on
    InstallationClasseeDTO toDTO(InstallationClassee installationClassee);
    
    List<AutocompleteDTO> toAutocompleteDTOs(List<InstallationClassee> installationClassees);
    
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "nom", target = "code"),
            @Mapping(source = "nom", target = "libelle")
    })
    AutocompleteDTO toAutocompleteDTO(InstallationClassee installationClassee);
}