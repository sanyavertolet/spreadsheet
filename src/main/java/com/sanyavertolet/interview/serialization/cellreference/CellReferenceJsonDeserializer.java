package com.sanyavertolet.interview.serialization.cellreference;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.sanyavertolet.interview.exceptions.CellReferenceException;
import com.sanyavertolet.interview.math.CellReference;

import java.io.IOException;

public class CellReferenceJsonDeserializer extends JsonDeserializer<CellReference> {
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
