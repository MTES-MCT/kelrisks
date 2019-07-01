package fr.gouv.beta.fabnum.kelrisks.facade.mapping.refentiel;

import fr.gouv.beta.fabnum.commun.utils.GeoJsonUtils;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteIndustrielBasolDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.ICommonMapperConfig;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.SiteIndustrielBasol;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(config = ICommonMapperConfig.class,
        imports = {GeoJsonUtils.class, Map.class, Collectors.class, Stream.class, AbstractMap.class})
public interface ISiteIndustrielBasolMapper extends IGeometryMapper {
    
    List<SiteIndustrielBasolDTO> toDTOs(List<SiteIndustrielBasol> siteIndustrielBasols);
    
    //    @formatter:off
    @Mappings(
            @Mapping(target = "ewkt", expression = "java(GeoJsonUtils.toGeoJson(siteIndustrielBasol.getPoint(), Stream.of(new AbstractMap.SimpleEntry<>(\"propriétaire\", siteIndustrielBasol.getProprietaire()), new AbstractMap.SimpleEntry<>(\"numéro\", siteIndustrielBasol.getNumerobasol())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))))")
    )
    //    @formatter:on
    SiteIndustrielBasolDTO toDTOs(SiteIndustrielBasol siteIndustrielBasol);
}