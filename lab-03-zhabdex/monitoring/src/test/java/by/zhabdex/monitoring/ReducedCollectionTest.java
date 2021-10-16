package by.zhabdex.monitoring;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

class ReducedCollectionTest implements FinalProcessedCollectionTest {
    @Test
    void Test1() {
        var collection = new ReducedCollection<>(Integer::sum);
        assertTrue(collection.currentState().isEmpty());

        assumeFalse(renewAndGetCurrentState(collection, List.of(4, 5)).isEmpty());
        assertEquals(collection.currentState().get(), 9);

        assumeFalse(renewAndGetCurrentState(collection, List.of(4, 5, 4, 4, 5)).isEmpty());
        assertEquals(collection.currentState().get(), 22);

        assumeFalse(renewAndGetCurrentState(collection, List.of(400)).isEmpty());
        assertEquals(collection.currentState().get(), 400);

        assertTrue(renewAndGetCurrentState(collection, List.of()).isEmpty());
    }

    @Test
    void Test2() {
        var collection = new ReducedCollection<>(String::concat);
        assertTrue(collection.currentState().isEmpty());

        assumeFalse(renewAndGetCurrentState(collection, List.of("ab", "acaba")).isEmpty());
        assertEquals(collection.currentState().get(), "abacaba");

        assumeFalse(renewAndGetCurrentState(collection, List.of("a", "b", "a", "b", "a", "a")).isEmpty());
        assertEquals(collection.currentState().get(), "ababaa");

        assertTrue(renewAndGetCurrentState(collection, List.of()).isEmpty());
    }
}