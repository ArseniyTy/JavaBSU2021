package by.zhabdex.monitoring;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.function.Function;

public class LimitedCollection<T> implements ProcessedCollection<T, T> {
    private final long maxSize;
    private List<? extends T> limitedElements = new ArrayList<>();

    LimitedCollection(long maxSize) {
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
