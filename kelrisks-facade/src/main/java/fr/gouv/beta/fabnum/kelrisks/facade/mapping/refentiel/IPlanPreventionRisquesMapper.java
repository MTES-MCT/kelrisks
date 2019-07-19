package fr.gouv.beta.fabnum.kelrisks.facade.mapping.refentiel;

import fr.gouv.beta.fabnum.commun.utils.GeoJsonUtils;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.PlanPreventionRisquesDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.ICommonMapperConfig;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.PlanPreventionRisques;

import java.util.AbstractMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.mapstruct.Mapper;

@Mapper(config = ICommonMapperConfig.class,
        imports = {GeoJsonUtils.class, Map.class, Collectors.class, Stream.class, AbstractMap.class})
public interface IPlanPreventionRisquesMapper extends IGeometryMapper {
    
    List<PlanPreventionRisquesDTO> toDTOs(List<PlanPreventionRisques> planPreventionRisques);
    
    PlanPreventionRisquesDTO toDTO(PlanPreventionRisques planPreventionRisques);
}