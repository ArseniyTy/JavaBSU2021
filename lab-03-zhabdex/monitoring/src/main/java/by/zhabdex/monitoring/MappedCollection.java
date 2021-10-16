package by.zhabdex.monitoring;

import java.util.*;
import java.util.function.Function;

public class MappedCollection<T, E> implements ProcessedCollection<T, E> {
    private final Function<T, E> mapper;
    private List<E> mappedElements = new ArrayList<>();
    private final HashMap<T, E> buffer = new HashMap<>();

    MappedCollection(Function<T, E> mapper) {
        this.mapper = mapper;
    }

    @Override
    public void renew(Collection<? extends T> elements) {
        elements.forEach(element -> buffer.computeIfAbsent(element, mapper));
        mappedElements = elements.stream().map(buffer::get).toList();
    }

    @Override
    public Collection<? extends E> currentState() {
        return mappedElements;
    }
}
