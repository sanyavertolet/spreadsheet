package com.sanyavertolet.interview.data.dependencies;

import com.sanyavertolet.interview.TestUtils;
import com.sanyavertolet.interview.exceptions.DataDependencyException;
import com.sanyavertolet.interview.exceptions.CellReferenceException;
import com.sanyavertolet.interview.math.CellReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

public class SimpleDependencyGraphTest {
    private final DependencyGraph dependencyGraph = new SimpleDependencyGraph();

    private final CellReference a1 = CellReference.of("A1");
    private final CellReference b1 = CellReference.of("B1");
    private final CellReference c1 = CellReference.of("C1");
    private final CellReference a2 = CellReference.of("A2");
    private final CellReference b2 = CellReference.of("B2");
    private final CellReference c2 = CellReference.of("C2");
    private final CellReference a3 = CellReference.of("A3");
    private final CellReference b3 = CellReference.of("B3");
    private final CellReference c3 = CellReference.of("C3");

    public SimpleDependencyGraphTest() throws CellReferenceException { }

    @Test
    void straightForwardDependenciesTest() throws DataDependencyException {
        dependencyGraph.addDependency(a1, b1);
        dependencyGraph.addDependency(b1, c1);
        dependencyGraph.addDependency(c1, a2);
        dependencyGraph.addDependency(a2, b2);
        dependencyGraph.addDependency(b2, c2);
        dependencyGraph.addDependency(c2, a3);
        dependencyGraph.addDependency(a3, b3);
        dependencyGraph.addDependency(b3, c3);

        List<CellReference> updateList = dependencyGraph.getUpdateList(a1);
        Assertions.assertEquals(9, updateList.size());
        Assertions.assertEquals(a1, updateList.get(0));
        Assertions.assertEquals(b1, updateList.get(1));
        Assertions.assertEquals(c1, updateList.get(2));
        Assertions.assertEquals(a2, updateList.get(3));
        Assertions.assertEquals(b2, updateList.get(4));
        Assertions.assertEquals(c2, updateList.get(5));
        Assertions.assertEquals(a3, updateList.get(6));
        Assertions.assertEquals(b3, updateList.get(7));
        Assertions.assertEquals(c3, updateList.get(8));
    }

    @Test
    void partialStraightForwardDependenciesTest() throws DataDependencyException {
        dependencyGraph.addDependency(a1, b1);
        dependencyGraph.addDependency(b1, c1);
        dependencyGraph.addDependency(c1, a2);
        dependencyGraph.addDependency(a2, b2);
        dependencyGraph.addDependency(b2, c2);
        dependencyGraph.addDependency(c2, a3);
        dependencyGraph.addDependency(a3, b3);
        dependencyGraph.addDependency(b3, c3);

        List<CellReference> updateList = dependencyGraph.getUpdateList(a3);
        Assertions.assertEquals(3, updateList.size());
        Assertions.assertEquals(a3, updateList.get(0));
        Assertions.assertEquals(b3, updateList.get(1));
        Assertions.assertEquals(c3, updateList.get(2));
    }

    @Test
    void selfDependencyTest() {
        Assertions.assertThrows(DataDependencyException.class, () -> dependencyGraph.addDependency(a1, a1));
    }

    @Test
    void dummyCyclicDependenciesTest() throws DataDependencyException {
        dependencyGraph.addDependency(a1, b1);
        dependencyGraph.addDependency(b1, c1);
        dependencyGraph.addDependency(c1, a1);
        Assertions.assertThrows(DataDependencyException.class, () -> dependencyGraph.getUpdateList(a1));
    }

    @Test
    void nonTrivialCyclicDependenciesTest() throws DataDependencyException {
        dependencyGraph.addDependency(a1, b1);
        dependencyGraph.addDependency(b1, c1);
        dependencyGraph.addDependency(c1, a2);
        dependencyGraph.addDependency(a1, a2);
        dependencyGraph.addDependency(b1, c3);
        dependencyGraph.addDependency(a1, c2);
        dependencyGraph.addDependency(c2, a1);

        List<CellReference> updateList = dependencyGraph.getUpdateList(b1);
        TestUtils.assertHappensBefore(b1, c1, updateList);
        TestUtils.assertHappensBefore(c1, a2, updateList);
        TestUtils.assertHappensBefore(b1, c3, updateList);
        Assertions.assertThrows(DataDependencyException.class, () -> dependencyGraph.getUpdateList(a1));
    }

    @Test
    void squareDependenciesTest() throws DataDependencyException {
        dependencyGraph.addDependency(a1, b1);
        dependencyGraph.addDependency(a1, c1);
        dependencyGraph.addDependency(b1, a2);
        dependencyGraph.addDependency(c1, a2);

        List<CellReference> updateList = dependencyGraph.getUpdateList(a1);
        TestUtils.assertHappensBefore(a1, b1, updateList);
        TestUtils.assertHappensBefore(a1, c1, updateList);
        TestUtils.assertHappensBefore(a1, a2, updateList);
        TestUtils.assertHappensBefore(b1, a2, updateList);
        TestUtils.assertHappensBefore(c1, a2, updateList);
    }
}
