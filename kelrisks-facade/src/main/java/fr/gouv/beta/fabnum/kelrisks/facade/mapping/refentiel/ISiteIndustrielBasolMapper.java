package fr.gouv.beta.fabnum.kelrisks.facade.mapping.refentiel;


import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteIndustrielBasolDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.ICommonMapperConfig;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.SiteIndustrielBasol;

import java.util.List;

import org.mapstruct.Mapper;


@Mapper(config = ICommonMapperConfig.class)
public interface ISiteIndustrielBasolMapper {
    
    List<SiteIndustrielBasolDTO> toDTOs(List<SiteIndustrielBasol> siteIndustrielBasol);
}