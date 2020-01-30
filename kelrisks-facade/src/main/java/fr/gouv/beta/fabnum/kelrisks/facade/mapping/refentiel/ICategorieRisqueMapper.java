package fr.gouv.beta.fabnum.kelrisks.facade.mapping.refentiel;

import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.CategorieRisqueDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.ICommonMapperConfig;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.CategorieRisque;

import java.util.List;

import org.mapstruct.Mapper;

@Mapper(config = ICommonMapperConfig.class)
public interface ICategorieRisqueMapper {
    
    List<CategorieRisqueDTO> toDTOs(List<CategorieRisque> categorieRisque);
    
    CategorieRisqueDTO toDTO(CategorieRisque categorieRisque);
}