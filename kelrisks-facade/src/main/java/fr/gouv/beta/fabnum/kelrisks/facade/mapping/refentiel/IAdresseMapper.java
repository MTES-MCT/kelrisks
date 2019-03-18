package fr.gouv.beta.fabnum.kelrisks.facade.mapping.refentiel;


import fr.gouv.beta.fabnum.commun.facade.dto.AutocompleteDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.AdresseDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.ICommonMapperConfig;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.Adresse;

import java.util.List;

import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.Named;


@Mapper(config = ICommonMapperConfig.class)
public interface IAdresseMapper {
    
    List<AdresseDTO> toDTOs(List<Adresse> adresse);
    
    AdresseDTO toDTO(Adresse adresse);
    
    @IterableMapping(qualifiedByName = "toRueAutoCompleteDTO")
    List<AutocompleteDTO> toRueAutoCompleteDTOs(List<Adresse> adresses);
    
    @Named("toRueAutoCompleteDTO")
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "code", source = "nomVoie"),
            @Mapping(target = "libelle", source = "nomVoie"),
    })
    AutocompleteDTO toRueAutoCompleteDTO(Adresse adresse);
    
    @IterableMapping(qualifiedByName = "toNumeroVoieCompleteDTO")
    List<AutocompleteDTO> toNumeroVoieAutoCompleteDTOs(List<Adresse> adresses);
    
    @Named("toNumeroVoieCompleteDTO")
    @Mappings({
            @Mapping(target = "id", source = "id"),
            @Mapping(target = "code", source = "idBAN"),
            @Mapping(target = "libelle", expression = "java(adresse.getNumero() + \" \" + adresse.getComplement())"),
    })
    AutocompleteDTO toNumeroVoieCompleteDTO(Adresse adresse);
}