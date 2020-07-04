package fr.gouv.beta.fabnum.kelrisks.facade.mapping.referentiel;

import fr.gouv.beta.fabnum.commun.utils.GeoJsonUtils;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.ArgileDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.ICommonMapperConfig;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.Argile;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.mapstruct.Mapper;

@Mapper(config = ICommonMapperConfig.class,
        imports = {GeoJsonUtils.class, Map.class, Collectors.class, Stream.class, AbstractMap.class})
public interface IArgileMapper extends IGeometryMapper {
    
    List<ArgileDTO> toDTOs(List<Argile> Argile);
    
    ArgileDTO toDTO(Argile Argile);
}