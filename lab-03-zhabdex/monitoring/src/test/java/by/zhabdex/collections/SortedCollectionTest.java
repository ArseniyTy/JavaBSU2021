package by.zhabdex.collections;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SortedCollectionTest implements ProcessedCollectionTest {
    @Test
    void Test1() {
        var collection = new SortedCollection<>(Integer::compare);
        assertTrue(collection.currentState().stream().toList().isEmpty());

        assertEquals(renewAndGetCurrentStateAsList(collection, List.of(4, 5)),
                List.of(4, 5));
        assertEquals(renewAndGetCurrentStateAsList(collection, List.of(6, 8, 7)),
                List.of(6, 7, 8));
        assertEquals(renewAndGetCurrentStateAsList(collection, List.of(7)),
                List.of(7));
        assertEquals(renewAndGetCurrentStateAsList(collection, List.of(100, 8, 7, 6)),
                List.of(6, 7, 8, 100));
        assertTrue(renewAndGetCurrentStateAsList(collection, List.of()).isEmpty());
    }

    @Test
    void Test2() {
        var collection = new SortedCollection<>(String::length);
        assertTrue(collection.currentState().stream().toList().isEmpty());

        assertEquals(renewAndGetCurrentStateAsList(collection, List.of("ab", "cd")),
                List.of("ab", "cd"));
        assertEquals(renewAndGetCurrentStateAsList(collection, List.of("a", "bcd", "ab")),
                List.of("a", "ab", "bcd"));
        assertTrue(renewAndGetCurrentStateAsList(collection, List.of()).isEmpty());
    }

    @Test
    void Test3() {
        var collection = new SortedCollection<>((Integer x) -> x, true);
        assertTrue(collection.currentState().stream().toList().isEmpty());

        assertEquals(renewAndGetCurrentStateAsList(collection, List.of(4, 5)),
                List.of(5, 4));
        assertEquals(renewAndGetCurrentStateAsList(collection, List.of(6, 8, 7)),
                List.of(8, 7, 6));
        assertEquals(renewAndGetCurrentStateAsList(collection, List.of(7)),
                List.of(7));
        assertEquals(renewAndGetCurrentStateAsList(collection, List.of(100, 8, 7, 6)),
                List.of(100, 8, 7, 6));
        assertTrue(renewAndGetCurrentStateAsList(collection, List.of()).isEmpty());
    }
}