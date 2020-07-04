package fr.gouv.beta.fabnum.kelrisks.facade.mapping.referentiel;

import fr.gouv.beta.fabnum.commun.utils.GeoJsonUtils;

import java.util.AbstractMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.geolatte.geom.Geometry;
import org.mapstruct.Named;

public interface IGeometryMapper {
    
    @Named("toWKT")
    default String toGeom(Geometry<?> geom) {
    
        Stream.of(new AbstractMap.SimpleEntry<>("code", 1),
                  new AbstractMap.SimpleEntry<>("nom", 2))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    
        return GeoJsonUtils.toGeoJson(geom);
    }
}
