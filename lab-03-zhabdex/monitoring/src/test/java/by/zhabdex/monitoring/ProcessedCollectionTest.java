package by.zhabdex.monitoring;

import java.util.Collection;
import java.util.List;

public interface ProcessedCollectionTest {
    default <T, E> List<? extends E> renewAndGetCurrentStateAsList(ProcessedCollection<T, E> collection,
                                                                   Collection<? extends T> elements) {
        collection.renew(elements);
        return collection.currentState().stream().toList();
    }
}
