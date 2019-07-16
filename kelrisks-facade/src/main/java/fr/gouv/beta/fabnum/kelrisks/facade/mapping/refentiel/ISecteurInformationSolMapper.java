package fr.gouv.beta.fabnum.kelrisks.facade.mapping.refentiel;

import fr.gouv.beta.fabnum.commun.utils.GeoJsonUtils;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SecteurInformationSolDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.ICommonMapperConfig;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.SecteurInformationSol;

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
public interface ISecteurInformationSolMapper extends IGeometryMapper {
    
    List<SecteurInformationSolDTO> toDTOs(List<SecteurInformationSol> secteurInformationSolList);
    
    //    @formatter:off
    @Mappings(
            @Mapping(target = "ewkt", expression = "java(GeoJsonUtils.toGeoJson(secteurInformationSol.getMultiPolygon(), Stream.of(new AbstractMap.SimpleEntry<>(\"Numéro\", secteurInformationSol.getNumero())).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))))")
    )
    //    @formatter:on
    SecteurInformationSolDTO toDTOs(SecteurInformationSol secteurInformationSol);
}