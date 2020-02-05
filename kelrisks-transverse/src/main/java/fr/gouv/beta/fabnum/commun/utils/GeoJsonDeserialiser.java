package fr.gouv.beta.fabnum.commun.utils;

import java.io.IOException;

import org.geolatte.geom.Geometry;
import org.springframework.boot.jackson.JsonComponent;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;

@JsonComponent
public class GeoJsonDeserialiser extends JsonDeserializer<Geometry<?>> {
    
    @Override
    public Geometry<?> deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        
        JsonNode node = jp.getCodec().readTree(jp);
        
        return GeoJsonUtils.fromGeoJson(node.toString());
    }
}