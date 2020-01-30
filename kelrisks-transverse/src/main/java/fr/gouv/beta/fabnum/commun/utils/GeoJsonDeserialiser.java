package fr.gouv.beta.fabnum.commun.utils;

import java.io.IOException;

import org.geolatte.geom.Geometry;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

public class GeoJsonDeserialiser extends StdDeserializer<Geometry<?>> {
    
    public GeoJsonDeserialiser() {
        
        this(null);
    }
    
    public GeoJsonDeserialiser(Class<?> vc) {
        
        super(vc);
    }
    
    @Override
    public Geometry<?> deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException {
        
        JsonNode node    = jp.getCodec().readTree(jp);
        
        return GeoJsonUtils.fromGeoJson(node.toString());
    }
}