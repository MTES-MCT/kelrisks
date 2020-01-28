package fr.gouv.beta.fabnum.kelrisks.facade.mapping.refentiel;

import fr.gouv.beta.fabnum.commun.utils.GeoJsonUtils;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.ParcelleDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.ICommonMapperConfig;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.Parcelle;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.geolatte.geom.Geometry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(config = ICommonMapperConfig.class,
        imports = {GeoJsonUtils.class, Map.class, Collectors.class, Stream.class, AbstractMap.class, StringUtils.class})
public interface IParcelleMapper extends IGeometryMapper {
    
    //    @formatter:off
    @Mappings(
            @Mapping(target = "ewkt",
                     expression = "java(GeoJsonUtils.toGeoJson(parcelle.getMultiPolygon()," +
                                  "                               Stream.of(new AbstractMap.SimpleEntry<>(\"code\", StringUtils.stripToEmpty(parcelle.getSection() + \"-\" + parcelle.getNumero()))," +
                                  "                                         new AbstractMap.SimpleEntry<>(\"commune\", StringUtils.stripToEmpty(parcelle.getCommune())))" +
                                  "                                       .collect(Collectors.toMap(Map.Entry::getKey," +
                                  "                                                                 Map.Entry::getValue))))")
    )
    //    @formatter:on
    ParcelleDTO toDTO(Parcelle parcelle);
    
    List<ParcelleDTO> toDTOs(List<Parcelle> parcelles);
    
    @Mappings(
            @Mapping(target = "ewkt", qualifiedByName = "toWKT")
    )
    ParcelleDTO toDTO(Geometry geometry);
}