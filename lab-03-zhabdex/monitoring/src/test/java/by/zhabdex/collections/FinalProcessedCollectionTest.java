package by.zhabdex.collections;

import by.zhabdex.collections.FinalProcessedCollection;

import java.util.Collection;

public interface FinalProcessedCollectionTest {
    default <T, E> E renewAndGetCurrentState(FinalProcessedCollection<T, E> collection,
                                             Collection<? extends T> elements) {
        collection.renew(elements);
        return collection.currentState();
    }
}
