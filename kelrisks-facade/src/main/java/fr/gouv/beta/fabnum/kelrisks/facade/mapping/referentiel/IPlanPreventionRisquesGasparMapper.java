package fr.gouv.beta.fabnum.kelrisks.facade.mapping.referentiel;

import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.PlanPreventionRisquesGasparDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.ICommonMapperConfig;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.PlanPreventionRisquesGaspar;

import java.util.List;

import org.mapstruct.Mapper;

@Mapper(config = ICommonMapperConfig.class)
public interface IPlanPreventionRisquesGasparMapper {
    
    List<PlanPreventionRisquesGasparDTO> toDTOs(List<PlanPreventionRisquesGaspar> planPreventionRisquesGaspar);
    
    PlanPreventionRisquesGasparDTO toDTO(PlanPreventionRisquesGaspar planPreventionRisquesGaspar);
}