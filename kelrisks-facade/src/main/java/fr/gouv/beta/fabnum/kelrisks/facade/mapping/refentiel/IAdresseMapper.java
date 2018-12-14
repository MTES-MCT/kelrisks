package fr.gouv.beta.fabnum.kelrisks.facade.mapping.refentiel;


import fr.gouv.beta.fabnum.commun.facade.dto.AutocompleteDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.AdresseDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.ICommonMapperConfig;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.Adresse;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;


@Mapper(config = ICommonMapperConfig.class)
public interface IAdresseMapper {
    
    List<AdresseDTO> toDTOs(List<Adresse> adresse);
    
    AdresseDTO toDTO(Adresse adresse);
    
    List<AutocompleteDTO> toAutoCompleteDTOs(List<Adresse> adresse);
    
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "code", source = "codePostal"),
            @Mapping(target = "libelle", expression = "java(adresse.getCodePostal() + ' - ' + adresse.getNomCommune())"),
    })
    AutocompleteDTO toAutoCompleteDTO(Adresse adresse);
}