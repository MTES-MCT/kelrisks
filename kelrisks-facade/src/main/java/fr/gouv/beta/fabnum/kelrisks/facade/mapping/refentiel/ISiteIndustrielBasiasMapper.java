package fr.gouv.beta.fabnum.kelrisks.facade.mapping.refentiel;


import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteIndustrielBasiasDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.ICommonMapperConfig;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.SiteIndustrielBasias;

import java.util.List;

import org.mapstruct.Mapper;


@Mapper(config = ICommonMapperConfig.class)
public interface ISiteIndustrielBasiasMapper {
    
    List<SiteIndustrielBasiasDTO> toDTOs(List<SiteIndustrielBasias> siteIndustrielBasias);
}