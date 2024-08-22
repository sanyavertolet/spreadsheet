package com.sanyavertolet.interview.serialization.cellreference;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.sanyavertolet.interview.exceptions.CellReferenceException;
import com.sanyavertolet.interview.math.CellReference;

import java.io.IOException;

/**
 * A custom JSON deserializer for {@link CellReference} objects, extending {@link JsonDeserializer}.
 * The {@code CellReferenceJsonDeserializer} is responsible for converting JSON string values into {@code CellReference} instances.
 */
public class CellReferenceJsonDeserializer extends JsonDeserializer<CellReference> {

    /**
     * Deserializes a JSON string into a {@link CellReference} object.
     * The string is expected to represent a valid cell reference identifier.
     *
     * @param jsonParser the parser used to read the JSON content.
     * @param context the context for the deserialization process.
     * @return the deserialized {@link CellReference} object.
     * @throws IOException if an I/O error occurs during deserialization.
     * @throws JsonParseException if the string cannot be parsed into a {@link CellReference}.
     */
    @Override
    public CellReference deserialize(JsonParser jsonParser, DeserializationContext context) throws IOException {
        String identifier = jsonParser.getValueAsString();
        try {
            return CellReference.of(identifier);
        } catch (CellReferenceException exception) {
            throw new JsonParseException(jsonParser, "Could not parse CellReference: " + identifier, exception);
        }
    }
}
