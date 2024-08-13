package com.sanyavertolet.interview.serialization.cellreference;

import com.fasterxml.jackson.databind.module.SimpleModule;
import com.sanyavertolet.interview.math.CellReference;

public class CustomCellReferenceModule extends SimpleModule {
    public CustomCellReferenceModule() {
        addSerializer(CellReference.class, new CellReferenceJsonSerializer());
        addDeserializer(CellReference.class, new CellReferenceJsonDeserializer());
    }
}
