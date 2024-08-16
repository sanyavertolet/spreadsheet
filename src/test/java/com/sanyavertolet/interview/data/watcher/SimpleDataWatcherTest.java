package com.sanyavertolet.interview.data.watcher;

import com.sanyavertolet.interview.data.Data;
import com.sanyavertolet.interview.data.accessor.DataAccessor;
import com.sanyavertolet.interview.exceptions.expressions.ExpressionEvaluationException;
import com.sanyavertolet.interview.math.expressions.Expression;
import com.sanyavertolet.interview.math.expressions.evaluator.ExpressionEvaluator;
import com.sanyavertolet.interview.math.expressions.evaluator.SimpleExpressionEvaluator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.sanyavertolet.interview.CellReferences.*;
import static com.sanyavertolet.interview.Expressions.BinaryExpressions.mul;
import static com.sanyavertolet.interview.Expressions.BinaryExpressions.plus;
import static com.sanyavertolet.interview.Expressions.Cells.cell;
import static com.sanyavertolet.interview.Expressions.Values.three;
import static com.sanyavertolet.interview.Expressions.Values.two;


public class SimpleDataWatcherTest {
    private Data a1;
    private Data a2;
    private Data a3;
    private Data a4;
    private final DataAccessor dataAccessor =  reference -> switch (reference.identifier()) {
        case "A1" -> a1;
        case "A2" -> a2;
        case "A3" -> a3;
        case "A4" -> a4;
        default -> null;
    };
    private final ExpressionEvaluator expressionEvaluator = new SimpleExpressionEvaluator(dataAccessor);
    private final DataWatcher dataWatcher = new SimpleDataWatcher(dataAccessor, expressionEvaluator);

    public SimpleDataWatcherTest() { }

    @BeforeEach
    void setUp() throws ExpressionEvaluationException {
        Expression a2Expression = mul(two, cell(a1Ref));
        Expression a3Expression = mul(three, cell(a1Ref));
        Expression a4Expression = plus(cell(a2Ref), cell(a3Ref));

        a1 = new Data("4", 4);
        a2 = new Data("=2 * A1", expressionEvaluator.evaluate(a2Expression), a2Expression);
        a3 = new Data("=3 * A1", expressionEvaluator.evaluate(a3Expression), a3Expression);
        a4 = new Data("=A2 + A3", expressionEvaluator.evaluate(a4Expression), a4Expression);
    }

    @Test
    void dummyDataUpdateTest() throws ExpressionEvaluationException {
        dataWatcher.update(a1, a1Ref);
        dataWatcher.update(a2, a2Ref);
        Assertions.assertEquals(2 * a1.getValue().asInteger(), a2.getValue().asInteger());
        a1 = new Data("5.0", 5.0);
        dataWatcher.update(a1, a1Ref);
        Assertions.assertEquals(2 * a1.getValue().asDouble(), a2.getValue().asDouble());
    }

    @Test
    void squareDependencyTest() throws ExpressionEvaluationException {
        dataWatcher.update(a1, a1Ref);
        dataWatcher.update(a2, a2Ref);
        dataWatcher.update(a3, a3Ref);
        dataWatcher.update(a4, a4Ref);
        Assertions.assertEquals(2 * a1.getValue().asInteger(), a2.getValue().asInteger());
        Assertions.assertEquals(3 * a1.getValue().asInteger(), a3.getValue().asInteger());
        Assertions.assertEquals(a2.getValue().plus(a3.getValue()), a4.getValue());

        a1 = new Data("5", 5.0);
        dataWatcher.update(a1, a1Ref);

        Assertions.assertEquals(2 * a1.getValue().asInteger(), a2.getValue().asInteger());
        Assertions.assertEquals(3 * a1.getValue().asInteger(), a3.getValue().asInteger());
        Assertions.assertEquals(a2.getValue().plus(a3.getValue()), a4.getValue());
    }

    @Test
    void cyclicDependencyTest() throws ExpressionEvaluationException {
        dataWatcher.update(a1, a1Ref);
        dataWatcher.update(a2, a2Ref);
        dataWatcher.update(a3, a3Ref);
        dataWatcher.update(a4, a4Ref);

        Expression newA2Expression = cell(a4Ref);
        a2 = new Data("=A4", expressionEvaluator.evaluate(newA2Expression), newA2Expression);
        dataWatcher.update(a2, a2Ref);

        Assertions.assertNull(a2.getValue());
        Assertions.assertNull(a4.getValue());
    }

    @Test
    void selfDependencyTest() throws ExpressionEvaluationException {
        dataWatcher.update(a1, a1Ref);
        dataWatcher.update(a2, a2Ref);
        dataWatcher.update(a3, a3Ref);
        dataWatcher.update(a4, a4Ref);

        Expression newA2Expression = cell(a2Ref);
        a2 = new Data("=A2", expressionEvaluator.evaluate(newA2Expression), newA2Expression);
        dataWatcher.update(a2, a2Ref);

        Assertions.assertNull(a2.getValue());
        Assertions.assertNull(a4.getValue());
    }
}
