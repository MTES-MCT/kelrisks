package fr.gouv.beta.fabnum.kelrisks.facade.mapping.refentiel;


import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.ParcelleDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.ICommonMapperConfig;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.Parcelle;

import org.mapstruct.Mapper;


@Mapper(config = ICommonMapperConfig.class)
public interface IParcelleMapper {
    
    ParcelleDTO toDTO(Parcelle parcelle);
}