package fr.gouv.beta.fabnum.kelrisks.facade.mapping;

import org.mapstruct.MapperConfig;
import org.mapstruct.NullValueCheckStrategy;

@MapperConfig(
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface ICommonMapperConfig {

}
