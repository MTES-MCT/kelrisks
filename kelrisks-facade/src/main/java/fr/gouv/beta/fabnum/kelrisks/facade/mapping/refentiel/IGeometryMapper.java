package fr.gouv.beta.fabnum.kelrisks.facade.mapping.refentiel;

import fr.gouv.beta.fabnum.commun.utils.GeoJsonUtils;

import org.geolatte.geom.Geometry;
import org.mapstruct.Named;

public interface IGeometryMapper {
    
    @Named("toWKT")
    default String toGeom(Geometry geom) {
        
        return GeoJsonUtils.toGeoJson(geom);
    }
}
