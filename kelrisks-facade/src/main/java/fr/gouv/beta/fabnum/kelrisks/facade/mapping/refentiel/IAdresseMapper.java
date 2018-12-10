package fr.gouv.beta.fabnum.kelrisks.facade.mapping.refentiel;


import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.AdresseDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.ICommonMapperConfig;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.Adresse;

import java.util.List;

import org.mapstruct.Mapper;


@Mapper(config = ICommonMapperConfig.class)
public interface IAdresseMapper {
    
    List<AdresseDTO> toDTOs(List<Adresse> adresse);
    
    AdresseDTO toDTO(Adresse adresse);
}