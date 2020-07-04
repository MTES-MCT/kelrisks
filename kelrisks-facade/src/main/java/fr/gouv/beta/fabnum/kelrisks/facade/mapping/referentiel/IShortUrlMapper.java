package fr.gouv.beta.fabnum.kelrisks.facade.mapping.referentiel;

import fr.gouv.beta.fabnum.kelrisks.facade.avis.ShortUrlDTO;
import fr.gouv.beta.fabnum.kelrisks.facade.mapping.ICommonMapperConfig;
import fr.gouv.beta.fabnum.kelrisks.transverse.referentiel.entities.ShortUrl;

import java.util.List;

import org.mapstruct.Mapper;

@Mapper(config = ICommonMapperConfig.class)
public interface IShortUrlMapper {
    
    ShortUrlDTO toDTO(ShortUrl shortUrl);
    
    ShortUrl toEntity(ShortUrlDTO shortUrlDTO);
    
    List<ShortUrlDTO> toDTOs(List<ShortUrl> rechercherAvecCritere);
}