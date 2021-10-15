package by.zhabdex.monitoring;

import java.util.Collection;
import java.util.HashSet;
import java.util.function.Predicate;

public class FilteredCollection<T> implements ProcessedCollection<T, T> {
    private final Predicate<T> predicate;
    private Collection<T> filteredElements;
    private final HashSet<T> buffer = new HashSet<>();

    FilteredCollection(Predicate<T> predicate) {
        this.predicate = predicate;
    }

    @Override
    public void renew(Collection<? extends T> elements) {

//        elements.forEach(element -> {
//            if (!buffer.contains(element)) {
//
//            }
//            buffer.add(element, mapper)
//        });
//        mappedElements = elements.stream().map(buffer::get).toList();
    }

    @Override
    public Collection<? extends T> currentState() {
        return filteredElements;
    }
}
