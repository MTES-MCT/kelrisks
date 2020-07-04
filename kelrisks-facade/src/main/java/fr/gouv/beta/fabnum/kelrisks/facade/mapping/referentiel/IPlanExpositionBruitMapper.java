package fr.gouv.beta.fabnum.kelrisks.facade.mapping.referentiel;

import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.PlanExpositionBruitDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.ICommonMapperConfig;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.PlanExpositionBruit;

import java.util.List;

import org.mapstruct.Mapper;

@Mapper(config = ICommonMapperConfig.class)
public interface IPlanExpositionBruitMapper {
    
    List<PlanExpositionBruitDTO> toDTOs(List<PlanExpositionBruit> planExpositionBruit);
    
    PlanExpositionBruitDTO toDTO(PlanExpositionBruit planExpositionBruit);
}