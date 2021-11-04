package by.zhabdex.collections;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FilteredCollectionTest implements ProcessedCollectionTest {
    @Test
    void mainTest() {
        var collection = new FilteredCollection<>((Integer a) -> a % 2 == 0);
        assertTrue(collection.currentState().stream().toList().isEmpty());

        assertEquals(renewAndGetCurrentStateAsList(collection, List.of(4, 5)),
                List.of(4));
        assertEquals(renewAndGetCurrentStateAsList(collection, List.of(6, 6)),
                List.of(6, 6));
        assertEquals(renewAndGetCurrentStateAsList(collection, List.of(6, 7, 8, 100)),
                List.of(6, 8, 100));
        assertEquals(renewAndGetCurrentStateAsList(collection, List.of(7, 9, 11)),
                List.of());
        assertTrue(renewAndGetCurrentStateAsList(collection, List.of()).isEmpty());
    }
}