package by.zhabdex.monitoring;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class MappedCollectionTest implements ProcessedCollectionTest {
    @Test
    void mainTest() {
        var collection = new MappedCollection<>((Integer a) -> "Number is " + a.toString());
        assertTrue(collection.currentState().stream().toList().isEmpty());

        assertEquals(renewAndGetCurrentStateAsList(collection, List.of(4, 5)),
                List.of("Number is 4", "Number is 5"));
        assertEquals(renewAndGetCurrentStateAsList(collection, List.of(6, 7)),
                List.of("Number is 6", "Number is 7"));
        assertEquals(renewAndGetCurrentStateAsList(collection, List.of(6, 7, 8, 100)),
                List.of("Number is 6", "Number is 7", "Number is 8", "Number is 100"));
        assertTrue(renewAndGetCurrentStateAsList(collection, List.of()).isEmpty());
    }
}