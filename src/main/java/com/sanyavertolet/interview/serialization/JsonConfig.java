package com.sanyavertolet.interview.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanyavertolet.interview.serialization.cellreference.CustomCellReferenceModule;

public class JsonConfig {
    private JsonConfig() { }

    public static ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new CustomCellReferenceModule());
        return objectMapper;
    }
}
