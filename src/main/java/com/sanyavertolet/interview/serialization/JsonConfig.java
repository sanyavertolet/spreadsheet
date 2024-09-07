package com.sanyavertolet.interview.serialization;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sanyavertolet.interview.serialization.cellreference.CustomCellReferenceModule;

/**
 * A utility class for configuring and providing an {@link ObjectMapper} instance for JSON processing.
 * The {@code JsonConfig} class is designed to create and configure an {@link ObjectMapper} with custom modules.
 */
final public class JsonConfig {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private JsonConfig() { }

    /**
     * Creates and configures an {@link ObjectMapper} with the necessary custom modules for the application.
     * The configured {@code ObjectMapper} includes the registration of a {@link CustomCellReferenceModule}.
     *
     * @return a configured {@link ObjectMapper} instance.
     */
    public static ObjectMapper createObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new CustomCellReferenceModule());
        return objectMapper;
    }
}
