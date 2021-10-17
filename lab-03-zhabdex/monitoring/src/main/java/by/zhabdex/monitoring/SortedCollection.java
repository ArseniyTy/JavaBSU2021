package by.zhabdex.monitoring;

import java.util.*;
import java.util.function.Function;

public class SortedCollection<T> implements ProcessedCollection<T, T> {
    private final Comparator<T> comparator;
    private List<? extends T> elements = new ArrayList<>();

    public SortedCollection(Comparator<T> comparator) {
        this.comparator = comparator;
    }

    public <E extends Comparable<? super E>> SortedCollection(Function<T, E> keyExtractor) {
        comparator = Comparator.comparing(keyExtractor);
    }

    public <E extends Comparable<? super E>> SortedCollection(Function<T, E> keyExtractor, boolean reversed) {
        comparator = reversed ? Comparator.comparing(keyExtractor).reversed() : Comparator.comparing(keyExtractor);
    }

    @Override
    public void renew(Collection<? extends T> elements) {
        this.elements = elements.stream().sorted(comparator).toList();
    }

    @Override
    public Collection<? extends T> currentState() {
        return elements;
    }
}
