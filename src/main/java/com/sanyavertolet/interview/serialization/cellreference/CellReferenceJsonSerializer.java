package com.sanyavertolet.interview.serialization.cellreference;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.sanyavertolet.interview.math.CellReference;

import java.io.IOException;

public class CellReferenceJsonSerializer extends JsonSerializer<CellReference> {
    @Override
    public void serialize(CellReference value, JsonGenerator jsonGenerator, SerializerProvider serializers) throws IOException {
        jsonGenerator.writeString(value.identifier());
    }
}
