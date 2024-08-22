package com.sanyavertolet.interview.data.factory;

import com.sanyavertolet.interview.data.Data;

/**
 * Interface for creating {@link Data} instances based on a given cell text.
 * Implementations of this interface are responsible for interpreting the provided text
 * and generating the appropriate {@link Data} object.
 */
public interface DataFactory {

    /**
     * Creates a {@link Data} instance based on the provided cell text.
     *
     * @param cellText the text representation of the data to be created.
     * @return a {@link Data} instance corresponding to the provided cell text.
     */
    Data create(String cellText);
}
