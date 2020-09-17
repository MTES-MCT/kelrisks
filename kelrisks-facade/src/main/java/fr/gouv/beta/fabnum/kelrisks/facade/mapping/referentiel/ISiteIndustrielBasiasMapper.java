package fr.gouv.beta.fabnum.kelrisks.facade.mapping.referentiel;

import fr.gouv.beta.fabnum.commun.facade.dto.AutocompleteDTO;
import fr.gouv.beta.fabnum.commun.utils.GeoJsonUtils;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteIndustrielBasiasDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.ICommonMapperConfig;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.SiteIndustrielBasias;

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
public interface ISiteIndustrielBasiasMapper extends IGeometryMapper {
    
    List<SiteIndustrielBasiasDTO> toDTOs(List<SiteIndustrielBasias> siteIndustrielBasias);
    
    //    @formatter:off
    @Mappings(
            @Mapping(target = "ewkt",
                     expression = "java(GeoJsonUtils.toGeoJson(siteIndustrielBasias.getPoint(), Stream.of(new AbstractMap.SimpleEntry<>(\"raisonSociale\", StringUtils.stripToEmpty(siteIndustrielBasias.getRaisonSociale())), new AbstractMap.SimpleEntry<>(\"identifiant\", StringUtils.stripToEmpty(siteIndustrielBasias.getIdentifiant()))).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))))")
    )
    //    @formatter:on
    SiteIndustrielBasiasDTO toDTO(SiteIndustrielBasias siteIndustrielBasias);
    
    List<AutocompleteDTO> toAutocompleteDTOs(List<SiteIndustrielBasias> siteIndustrielBasiasList);
    
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "raisonSociale", target = "code"),
            @Mapping(source = "raisonSociale", target = "libelle")
    })
    AutocompleteDTO toAutocompleteDTO(SiteIndustrielBasias siteIndustrielBasias);
}