package fr.gouv.beta.fabnum.commun.utils;

import java.io.IOException;

import org.geolatte.geom.Geometry;
import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

@JsonComponent
public class GeoJsonSerialiser extends JsonSerializer<Geometry<?>> {
    
    @Override
    public void serialize(Geometry<?> geom, JsonGenerator gen, SerializerProvider serializers) throws IOException {
        
        gen.writeString(GeoJsonUtils.toGeoJson(geom));
    }
}