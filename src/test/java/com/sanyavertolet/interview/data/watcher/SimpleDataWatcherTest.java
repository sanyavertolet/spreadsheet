package com.sanyavertolet.interview.data.watcher;

import com.sanyavertolet.interview.data.Data;
import com.sanyavertolet.interview.data.DoubleData;
import com.sanyavertolet.interview.data.ExpressionData;
import com.sanyavertolet.interview.data.manager.DataAccessor;
import com.sanyavertolet.interview.exceptions.CellReferenceException;
import com.sanyavertolet.interview.math.CellReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.sanyavertolet.interview.Expressions.BinaryExpressions.mul;
import static com.sanyavertolet.interview.Expressions.BinaryExpressions.plus;
import static com.sanyavertolet.interview.Expressions.Cells.cell;
import static com.sanyavertolet.interview.Expressions.Numbers.three;
import static com.sanyavertolet.interview.Expressions.Numbers.two;


public class SimpleDataWatcherTest {
    private final CellReference a1Ref = CellReference.of("A1");
    private final CellReference a2Ref = CellReference.of("A2");
    private final CellReference a3Ref = CellReference.of("A3");
    private final CellReference a4Ref = CellReference.of("A4");

    private DoubleData a1;
    private ExpressionData a2;
    private ExpressionData a3;
    private ExpressionData a4;
    private final DataAccessor dataAccessor = new DataAccessor() {
        @Override
        public Double getDoubleCellValue(CellReference cellReference) {
            return 0.0;
        }

        @Override
        public Data getData(CellReference reference) {
            return switch (reference.identifier()) {
                case "A1" -> a1;
                case "A2" -> a2;
                case "A3" -> a3;
                case "A4" -> a4;
                default -> null;
            };
        }

        @Override
        public boolean hasCell(CellReference cellReference) {
            return false;
        }
    };


    private final DataWatcher dataWatcher = new SimpleDataWatcher(dataAccessor);

    public SimpleDataWatcherTest() throws CellReferenceException { }

    @BeforeEach
    void setUp() {
        a1 = new DoubleData("4", 4.0);
        a2 = new ExpressionData("=2 * A1", mul(two, cell(a1Ref)), (args) -> 2 * a1.getValue());
        a3 = new ExpressionData("=3 * A1", mul(three, cell(a1Ref)), (args) -> 3 * a1.getValue());
        a4 = new ExpressionData("=A2 + A3", plus(cell(a2Ref), cell(a3Ref)), (args) -> a2.getValue() + a3.getValue());
    }

    @Test
    void dummyDataUpdateTest() {
        dataWatcher.update(a1, a1Ref);
        dataWatcher.update(a2, a2Ref);
        Assertions.assertEquals(2 * a1.getValue(), a2.getValue());
        a1 = new DoubleData("5", 5.0);
        dataWatcher.update(a1, a1Ref);
        Assertions.assertEquals(2 * a1.getValue(), a2.getValue());
    }

    @Test
    void squareDependencyTest() {
        dataWatcher.update(a1, a1Ref);
        dataWatcher.update(a2, a2Ref);
        dataWatcher.update(a3, a3Ref);
        dataWatcher.update(a4, a4Ref);
        Assertions.assertEquals(2 * a1.getValue(), a2.getValue());
        Assertions.assertEquals(3 * a1.getValue(), a3.getValue());
        Assertions.assertEquals(a2.getValue() + a3.getValue(), a4.getValue());

        a1 = new DoubleData("5", 5.0);
        dataWatcher.update(a1, a1Ref);

        Assertions.assertEquals(2 * a1.getValue(), a2.getValue());
        Assertions.assertEquals(3 * a1.getValue(), a3.getValue());
        Assertions.assertEquals(a2.getValue() + a3.getValue(), a4.getValue());
    }

    @Test
    void cyclicDependencyTest() {
        dataWatcher.update(a1, a1Ref);
        dataWatcher.update(a2, a2Ref);
        dataWatcher.update(a3, a3Ref);
        dataWatcher.update(a4, a4Ref);

        a2 = new ExpressionData("=A4", cell(a4Ref), (args) -> a4.getValue());
        dataWatcher.update(a2, a2Ref);

        Assertions.assertNull(a2.getValue());
        Assertions.assertNull(a4.getValue());
    }

    @Test
    void selfDependencyTest() {
        dataWatcher.update(a1, a1Ref);
        dataWatcher.update(a2, a2Ref);
        dataWatcher.update(a3, a3Ref);
        dataWatcher.update(a4, a4Ref);
        a2 = new ExpressionData("=A2", cell(a2Ref), (args) -> a2.getValue());
        dataWatcher.update(a2, a2Ref);

        Assertions.assertNull(a2.getValue());
        Assertions.assertNull(a4.getValue());
    }
}
