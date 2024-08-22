package com.sanyavertolet.interview.data.dependencies;

import com.sanyavertolet.interview.TestUtils;
import com.sanyavertolet.interview.exceptions.data.DataDependencyException;
import com.sanyavertolet.interview.math.CellReference;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.sanyavertolet.interview.CellReferences.*;

public class TopologicallySortedDependencyGraphTest {
    private final DependencyGraph dependencyGraph = new TopologicallySortedDependencyGraph();

    public TopologicallySortedDependencyGraphTest() { }

    @Test
    void straightForwardDependenciesTest() throws DataDependencyException {
        dependencyGraph.addDependency(a1Ref, b1Ref);
        dependencyGraph.addDependency(b1Ref, c1Ref);
        dependencyGraph.addDependency(c1Ref, a2Ref);
        dependencyGraph.addDependency(a2Ref, b2Ref);
        dependencyGraph.addDependency(b2Ref, c2Ref);
        dependencyGraph.addDependency(c2Ref, a3Ref);
        dependencyGraph.addDependency(a3Ref, b3Ref);
        dependencyGraph.addDependency(b3Ref, c3Ref);

        List<CellReference> updateList = dependencyGraph.getUpdateList(a1Ref);
        Assertions.assertEquals(8, updateList.size());
        Assertions.assertEquals(b1Ref, updateList.get(0));
        Assertions.assertEquals(c1Ref, updateList.get(1));
        Assertions.assertEquals(a2Ref, updateList.get(2));
        Assertions.assertEquals(b2Ref, updateList.get(3));
        Assertions.assertEquals(c2Ref, updateList.get(4));
        Assertions.assertEquals(a3Ref, updateList.get(5));
        Assertions.assertEquals(b3Ref, updateList.get(6));
        Assertions.assertEquals(c3Ref, updateList.get(7));
    }

    @Test
    void partialStraightForwardDependenciesTest() throws DataDependencyException {
        dependencyGraph.addDependency(a1Ref, b1Ref);
        dependencyGraph.addDependency(b1Ref, c1Ref);
        dependencyGraph.addDependency(c1Ref, a2Ref);
        dependencyGraph.addDependency(a2Ref, b2Ref);
        dependencyGraph.addDependency(b2Ref, c2Ref);
        dependencyGraph.addDependency(c2Ref, a3Ref);
        dependencyGraph.addDependency(a3Ref, b3Ref);
        dependencyGraph.addDependency(b3Ref, c3Ref);

        List<CellReference> updateList = dependencyGraph.getUpdateList(a3Ref);
        Assertions.assertEquals(2, updateList.size());
        Assertions.assertEquals(b3Ref, updateList.get(0));
        Assertions.assertEquals(c3Ref, updateList.get(1));
    }

    @Test
    void selfDependencyTest() {
        Assertions.assertThrows(DataDependencyException.class, () -> dependencyGraph.addDependency(a1Ref, a1Ref));
    }

    @Test
    void dummyCyclicDependenciesTest() throws DataDependencyException {
        dependencyGraph.addDependency(a1Ref, b1Ref);
        dependencyGraph.addDependency(b1Ref, c1Ref);
        dependencyGraph.addDependency(c1Ref, a1Ref);
        Assertions.assertThrows(DataDependencyException.class, () -> dependencyGraph.getUpdateList(a1Ref));
    }

    @Test
    void nonTrivialCyclicDependenciesTest() throws DataDependencyException {
        dependencyGraph.addDependency(a1Ref, b1Ref);
        dependencyGraph.addDependency(b1Ref, c1Ref);
        dependencyGraph.addDependency(c1Ref, a2Ref);
        dependencyGraph.addDependency(a1Ref, a2Ref);
        dependencyGraph.addDependency(b1Ref, c3Ref);
        dependencyGraph.addDependency(a1Ref, c2Ref);
        dependencyGraph.addDependency(c2Ref, a1Ref);

        List<CellReference> updateList = dependencyGraph.getUpdateList(b1Ref);
        Assertions.assertEquals(3, updateList.size());
        TestUtils.assertHappensBefore(c1Ref, a2Ref, updateList);
        Assertions.assertThrows(DataDependencyException.class, () -> dependencyGraph.getUpdateList(a1Ref));
    }

    @Test
    void squareDependenciesTest() throws DataDependencyException {
        dependencyGraph.addDependency(a1Ref, b1Ref);
        dependencyGraph.addDependency(a1Ref, c1Ref);
        dependencyGraph.addDependency(b1Ref, a2Ref);
        dependencyGraph.addDependency(c1Ref, a2Ref);

        List<CellReference> updateList = dependencyGraph.getUpdateList(a1Ref);
        TestUtils.assertHappensBefore(b1Ref, a2Ref, updateList);
        TestUtils.assertHappensBefore(c1Ref, a2Ref, updateList);
    }
}
