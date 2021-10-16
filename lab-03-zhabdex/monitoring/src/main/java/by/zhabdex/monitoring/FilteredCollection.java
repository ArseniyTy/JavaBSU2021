package by.zhabdex.monitoring;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;

public class FilteredCollection<T> implements ProcessedCollection<T, T> {
    private final Predicate<T> predicate;
    private List<? extends T> filteredElements = new ArrayList<>();
    private final HashSet<T> buffer = new HashSet<>();

    FilteredCollection(Predicate<T> predicate) {
        this.predicate = predicate;
    }

    @Override
    public void renew(Collection<? extends T> elements) {
        filteredElements = elements.stream().filter(element -> {
            if (buffer.contains(element)) {
                return true;
            }
            if (predicate.test(element)) {
                buffer.add(element);
                return true;
            }
            return false;
        }).toList();
    }

    @Override
    public Collection<? extends T> currentState() {
        return filteredElements;
    }
}
