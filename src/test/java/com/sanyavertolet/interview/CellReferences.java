package com.sanyavertolet.interview;

import com.sanyavertolet.interview.exceptions.CellReferenceException;
import com.sanyavertolet.interview.math.CellReference;

public class CellReferences {
    private CellReferences() {}
    public static CellReference a1Ref;
    public static CellReference a2Ref;
    public static CellReference a3Ref;
    public static CellReference a4Ref;

    public static CellReference b1Ref;
    public static CellReference b2Ref;
    public static CellReference b3Ref;

    public static CellReference c1Ref;
    public static CellReference c2Ref;
    public static CellReference c3Ref;

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
