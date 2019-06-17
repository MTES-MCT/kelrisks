package fr.gouv.beta.fabnum.kelrisks.facade.mapping.refentiel;

import fr.gouv.beta.fabnum.commun.facade.dto.AutocompleteDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.dto.referentiel.SiteIndustrielBasiasDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.ICommonMapperConfig;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.SiteIndustrielBasias;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(config = ICommonMapperConfig.class)
public interface ISiteIndustrielBasiasMapper extends IGeometryMapper {
    
    List<SiteIndustrielBasiasDTO> toDTOs(List<SiteIndustrielBasias> siteIndustrielBasias);
    
    @Mappings(
            @Mapping(target = "ewkt", source = "point", qualifiedByName = "toWKT")
    )
    SiteIndustrielBasiasDTO toDTO(SiteIndustrielBasias siteIndustrielBasias);
    
    List<AutocompleteDTO> toAutocompleteDTOs(List<SiteIndustrielBasias> siteIndustrielBasiasList);
    
    @Mappings({
            @Mapping(source = "id", target = "id"),
            @Mapping(source = "raisonSociale", target = "code"),
            @Mapping(source = "raisonSociale", target = "libelle")
    })
    AutocompleteDTO toAutocompleteDTO(SiteIndustrielBasias siteIndustrielBasias);
}