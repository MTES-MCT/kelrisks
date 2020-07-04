package fr.gouv.beta.fabnum.kelrisks.facade.mapping.referentiel;

import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.CommuneDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.ICommonMapperConfig;
import fr.gouv.beta.fabnum.kelrisks.transverse.apiclient.AdresseDataGouvPaginatedFeatures;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(config = ICommonMapperConfig.class)
public interface IAdresseDataGouvMapper {
    
    List<CommuneDTO> toDTOs(List<AdresseDataGouvPaginatedFeatures.Adresse> commune);
    
    @Mappings({
            @Mapping(source = "properties.city", target = "nomCommune"),
            @Mapping(source = "properties.citycode", target = "codeINSEE"),
            @Mapping(source = "properties.postcode", target = "codePostal")
    })
    CommuneDTO toDTO(AdresseDataGouvPaginatedFeatures.Adresse commune);
}