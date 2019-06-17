package fr.gouv.beta.fabnum.kelrisks.facade.mapping.refentiel;

import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteIndustrielBasolDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.ICommonMapperConfig;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.SiteIndustrielBasol;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(config = ICommonMapperConfig.class)
public interface ISiteIndustrielBasolMapper extends IGeometryMapper {
    
    List<SiteIndustrielBasolDTO> toDTOs(List<SiteIndustrielBasol> siteIndustrielBasols);
    
    @Mappings(
            @Mapping(target = "ewkt", source = "point", qualifiedByName = "toWKT")
    )
    SiteIndustrielBasolDTO toDTOs(SiteIndustrielBasol siteIndustrielBasol);
}