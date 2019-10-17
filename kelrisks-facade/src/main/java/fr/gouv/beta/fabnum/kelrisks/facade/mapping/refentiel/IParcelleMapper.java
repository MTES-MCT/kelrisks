package fr.gouv.beta.fabnum.kelrisks.facade.mapping.refentiel;

import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.ParcelleDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.ICommonMapperConfig;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.Parcelle;

import java.util.List;

import org.geolatte.geom.Geometry;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(config = ICommonMapperConfig.class)
public interface IParcelleMapper extends IGeometryMapper {
    
    @Mappings(
            @Mapping(target = "ewkt", source = "multiPolygon", qualifiedByName = "toWKT")
    )
    ParcelleDTO toDTO(Parcelle parcelle);
    
    List<ParcelleDTO> toDTOs(List<Parcelle> parcelles);
    
    @Mappings(
            @Mapping(target = "ewkt", qualifiedByName = "toWKT")
    )
    ParcelleDTO toDTO(Geometry geometry);
}