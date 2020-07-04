package fr.gouv.beta.fabnum.kelrisks.facade.mapping.referentiel;

import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.CommuneDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.ICommonMapperConfig;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.Commune;

import java.util.List;

import org.mapstruct.Mapper;

@Mapper(config = ICommonMapperConfig.class)
public interface ICommuneMapper {
    
    List<CommuneDTO> toDTOs(List<Commune> commune);
    
    CommuneDTO toDTO(Commune commune);
}