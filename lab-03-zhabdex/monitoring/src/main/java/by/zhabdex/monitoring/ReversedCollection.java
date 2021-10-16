package by.zhabdex.monitoring;

import java.util.*;

public class ReversedCollection<T> implements ProcessedCollection<T, T> {
    private List<? extends T> elements = new ArrayList<>();

    @Override
    public void renew(Collection<? extends T> elements) {
        this.elements = elements.stream().toList();
    }

    // returns just reversed view
    @Override
    public Collection<? extends T> currentState() {
        return new AbstractList<>() {
            @Override
            public T get(int index) {
                return elements.get(elements.size() - 1 - index);
            }

            @Override
            public int size() {
                return elements.size();
            }
        };
    }
}
