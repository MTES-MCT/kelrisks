package fr.gouv.beta.fabnum.kelrisks.facade.mapping.refentiel;


import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteSolPolueDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.ICommonMapperConfig;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.SiteSolPolue;

import org.mapstruct.Mapper;


@Mapper(config = ICommonMapperConfig.class)
public interface ISiteSolPolueMapper {
    
    SiteSolPolueDTO toDTO(SiteSolPolue siteSolPolue);
}