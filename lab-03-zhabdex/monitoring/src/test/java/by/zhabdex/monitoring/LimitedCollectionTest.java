package by.zhabdex.monitoring;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LimitedCollectionTest implements ProcessedCollectionTest {
    @Test
    void Test1() {
        var collection = new LimitedCollection<>(2);
        assertTrue(collection.currentState().stream().toList().isEmpty());

        assertEquals(renewAndGetCurrentStateAsList(collection, List.of(4, 5)),
                List.of(4, 5));
        assertEquals(renewAndGetCurrentStateAsList(collection, List.of(6, 6, 7)),
                List.of(6, 6));
        assertEquals(renewAndGetCurrentStateAsList(collection, List.of(7)),
                List.of(7));
        assertEquals(renewAndGetCurrentStateAsList(collection, List.of(6, 7, 8, 100)),
                List.of(6, 7));
        assertTrue(renewAndGetCurrentStateAsList(collection, List.of()).isEmpty());
    }

    @Test
    void Test2() {
        var collection = new LimitedCollection<>(0);
        assertTrue(collection.currentState().stream().toList().isEmpty());
        assertTrue(renewAndGetCurrentStateAsList(collection, List.of(1, 2, 3)).isEmpty());
        assertTrue(renewAndGetCurrentStateAsList(collection, List.of(1)).isEmpty());
        assertTrue(renewAndGetCurrentStateAsList(collection, List.of()).isEmpty());
    }

    @Test
    void Test3() {
        var collection = new LimitedCollection<String>(1);
        assertTrue(collection.currentState().stream().toList().isEmpty());

        assertEquals(renewAndGetCurrentStateAsList(collection, List.of("ab", "cd")),
                List.of("ab"));
        assertEquals(renewAndGetCurrentStateAsList(collection, List.of("a")),
                List.of("a"));
        assertTrue(renewAndGetCurrentStateAsList(collection, List.of()).isEmpty());
    }
}