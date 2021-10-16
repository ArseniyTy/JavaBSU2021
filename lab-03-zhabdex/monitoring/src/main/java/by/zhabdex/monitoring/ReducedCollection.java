package by.zhabdex.monitoring;

import java.util.*;
import java.util.function.BinaryOperator;

public class ReducedCollection<T> implements FinalProcessedCollection<T, Optional<T>> {
    private final BinaryOperator<T> reducer;
    private T currentState = null;
//    private List<E> mappedElements = new ArrayList<>();
//    private final HashMap<T, E> buffer = new HashMap<>();
    // optimization: хранить все элементы и результаты редьюсов для префиксов, потом, если префикс совпадает, можно
    // его переиспользовать

    ReducedCollection(BinaryOperator<T> reducer) {
        this.reducer = reducer;
    }

    @Override
    public void renew(Collection<? extends T> elements) {
//        currentState = elements.stream().map(element -> (T) element).reduce(reducer);
        var firstElement = elements.stream().findFirst();
        if (firstElement.isPresent()) {
            currentState = elements.stream().skip(1).reduce(firstElement.get(), reducer, reducer);
        } else {
            currentState = null;
        }
    }

    @Override
    public Optional<T> currentState() {
        return Optional.ofNullable(currentState);
    }
}
