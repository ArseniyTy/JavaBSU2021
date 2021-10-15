package by.zhabdex.monitoring;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.function.Function;
import java.util.stream.Collectors;

public class MappedCollection<T, E> implements ProcessedCollection<T, E> {
    private final Function<T, E> mapper;
    private Collection<E> mappedElements;
    private final HashMap<T, E> buffer = new HashMap<>();

    MappedCollection(Function<T, E> mapper) {
        this.mapper = mapper;
    }

    @Override
    public void renew(Collection<? extends T> elements) {
        elements.forEach(element -> buffer.computeIfAbsent(element, mapper));
        mappedElements = elements.stream().map(buffer::get).toList();
//        mappedElements = elements.stream().map(buffer::get).collect(Collectors.toCollection(elements.getClass()));
    }

    @Override
    public Collection<? extends E> currentState() {
        return mappedElements;
    }
}
