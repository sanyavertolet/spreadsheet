package com.sanyavertolet.interview.serialization.cellreference;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.sanyavertolet.interview.math.CellReference;

import java.io.Serial;

/**
 * A custom Jackson module for handling the serialization and deserialization of {@link CellReference} objects.
 * The {@code CustomCellReferenceModule} extends {@link SimpleModule} and registers custom serializers
 * and deserializers for the {@link CellReference} class.
 */
public class CustomCellReferenceModule extends SimpleModule {
    @Serial
    private static final long serialVersionUID = 42L;

    /**
     * Constructs a {@code CustomCellReferenceModule} and registers the custom {@link CellReferenceJsonSerializer}
     * and {@link CellReferenceJsonDeserializer} for handling {@link CellReference} objects.
     */
    public CustomCellReferenceModule() {
        addSerializer(CellReference.class, new CellReferenceJsonSerializer());
        addDeserializer(CellReference.class, new CellReferenceJsonDeserializer());
    }
}
