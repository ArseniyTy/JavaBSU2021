package by.zhabdex.monitoring;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ReversedCollectionTest implements ProcessedCollectionTest {
    @Test
    void Test1() {
        var collection = new ReversedCollection<>();
        assertTrue(collection.currentState().stream().toList().isEmpty());

        assertEquals(renewAndGetCurrentStateAsList(collection, List.of(4, 5)),
                List.of(5, 4));
        assertEquals(renewAndGetCurrentStateAsList(collection, List.of(6, 6, 7)),
                List.of(7, 6, 6));
        assertEquals(renewAndGetCurrentStateAsList(collection, List.of(7)),
                List.of(7));
        assertEquals(renewAndGetCurrentStateAsList(collection, List.of(6, 7, 8, 100)),
                List.of(100, 8, 7, 6));
        assertTrue(renewAndGetCurrentStateAsList(collection, List.of()).isEmpty());
    }

    @Test
    void Test2() {
        var collection = new ReversedCollection<String>();
        assertTrue(collection.currentState().stream().toList().isEmpty());

        assertEquals(renewAndGetCurrentStateAsList(collection, List.of("ab", "cd")),
                List.of("cd", "ab"));
        assertEquals(renewAndGetCurrentStateAsList(collection, List.of("a")),
                List.of("a"));
        assertTrue(renewAndGetCurrentStateAsList(collection, List.of()).isEmpty());
    }
}