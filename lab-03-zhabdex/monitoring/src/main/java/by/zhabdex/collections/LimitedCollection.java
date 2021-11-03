package by.zhabdex.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class LimitedCollection<T> implements ProcessedCollection<T, T> {
    private final long maxSize;
    private List<? extends T> limitedElements = new ArrayList<>();

    public LimitedCollection(long maxSize) {
        this.maxSize = maxSize;
    }

    @Override
    public void renew(Collection<? extends T> elements) {
        limitedElements = elements.stream().limit(maxSize).toList();
    }

    @Override
    public Collection<? extends T> currentState() {
        return limitedElements;
    }
}
