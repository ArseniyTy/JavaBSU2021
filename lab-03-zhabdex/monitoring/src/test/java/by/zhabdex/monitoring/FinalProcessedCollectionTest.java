package by.zhabdex.monitoring;

import java.util.Collection;
import java.util.List;

public interface FinalProcessedCollectionTest {
    default <T, E> E renewAndGetCurrentState(FinalProcessedCollection<T, E> collection,
                                             Collection<? extends T> elements) {
        collection.renew(elements);
        return collection.currentState();
    }
}
