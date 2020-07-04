package fr.gouv.beta.fabnum.kelrisks.facade.mapping.referentiel;

import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.CommuneDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.ICommonMapperConfig;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.GeoDataGouvCommune;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(config = ICommonMapperConfig.class)
public interface IGeoDataGouvMapper {
    
    List<CommuneDTO> toDTOs(List<GeoDataGouvCommune> commune);
    
    @Mappings({
            @Mapping(source = "nom", target = "nomCommune"),
            @Mapping(source = "code", target = "codeINSEE")
    })
    CommuneDTO toDTO(GeoDataGouvCommune commune);
}