package fr.gouv.beta.fabnum.kelrisks.facade.mapping.referentiel;

import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteSolPolueDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.ICommonMapperConfig;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.SiteSolPolue;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(config = ICommonMapperConfig.class)
public interface ISiteSolPolueMapper extends IGeometryMapper {
    
    @Mappings(
            @Mapping(target = "ewkt", source = "multiPolygon", qualifiedByName = "toWKT")
    )
    SiteSolPolueDTO toDTO(SiteSolPolue siteSolPolue);
    
    List<SiteSolPolueDTO> toDTOs(List<SiteSolPolue> siteSolPolueList);
}