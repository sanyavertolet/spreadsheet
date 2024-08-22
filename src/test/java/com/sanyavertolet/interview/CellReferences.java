package com.sanyavertolet.interview;

import com.sanyavertolet.interview.exceptions.CellReferenceException;
import com.sanyavertolet.interview.math.CellReference;

/**
 * A utility class that provides static references to common {@code CellReference} objects.
 * <p>
 * This class initializes and stores references for cells A1 through C3, which can be used throughout tests
 * or other parts of the application where common cell references are needed.
 * <p>
 * This class is not meant to be instantiated.
 */
public class CellReferences {

    /**
     * Private constructor to prevent instantiation of this utility class.
     */
    private CellReferences() {}

    /** A static cell reference for cell A1. */
    public static CellReference a1Ref;

    /** A static cell reference for cell A2. */
    public static CellReference a2Ref;

    /** A static cell reference for cell A3. */
    public static CellReference a3Ref;

    /** A static cell reference for cell A4. */
    public static CellReference a4Ref;

    /** A static cell reference for cell B1. */
    public static CellReference b1Ref;

    /** A static cell reference for cell B2. */
    public static CellReference b2Ref;

    /** A static cell reference for cell B3. */
    public static CellReference b3Ref;

    /** A static cell reference for cell C1. */
    public static CellReference c1Ref;

    /** A static cell reference for cell C2. */
    public static CellReference c2Ref;

    /** A static cell reference for cell C3. */
    public static CellReference c3Ref;

    /*
      Static initializer to create and initialize the cell references.
      <p>
      This block attempts to create {@code CellReference} objects for cells A1 through C3. If an exception
      occurs during the creation of any {@code CellReference}, it wraps and throws the exception as a
      {@code RuntimeException}.
     */
    static {
        try {
            a1Ref = CellReference.of("A1");
            a2Ref = CellReference.of("A2");
            a3Ref = CellReference.of("A3");
            a4Ref = CellReference.of("A4");

            b1Ref = CellReference.of("B1");
            b2Ref = CellReference.of("B2");
            b3Ref = CellReference.of("B3");

            c1Ref = CellReference.of("C1");
            c2Ref = CellReference.of("C2");
            c3Ref = CellReference.of("C3");
        } catch (CellReferenceException e) {
            throw new RuntimeException(e);
        }
    }
}
