package com.sanyavertolet.interview.serialization.cellreference;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.sanyavertolet.interview.math.CellReference;

import java.io.IOException;

/**
 * A custom JSON serializer for {@link CellReference} objects, extending {@link JsonSerializer}.
 * The {@code CellReferenceJsonSerializer} is responsible for converting {@code CellReference} instances into JSON string values.
 */
public class CellReferenceJsonSerializer extends JsonSerializer<CellReference> {

    /**
     * Serializes a {@link CellReference} object into a JSON string.
     * The string represents the cell reference identifier.
     *
     * @param value the {@link CellReference} object to serialize.
     * @param jsonGenerator the generator used to write JSON content.
     * @param serializers the provider that can be used to get serializers for serializing the value's properties.
     * @throws IOException if an I/O error occurs during serialization.
     */
    @Override
    public void serialize(CellReference value, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
        jsonGenerator.writeString(value.identifier());
    }
}
