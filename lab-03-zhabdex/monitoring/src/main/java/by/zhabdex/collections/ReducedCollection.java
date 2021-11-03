package by.zhabdex.collections;

import java.util.*;
import java.util.function.BinaryOperator;

public class ReducedCollection<T> implements FinalProcessedCollection<T, Optional<T>> {
    private final BinaryOperator<T> reducer;
    private T currentState = null;
    // optimization: хранить все элементы и результаты редьюсов для префиксов, потом использовать наидлинейнший
    // совпадающий префикс. Но это слишком много по памяти, и чекать потом много. Можно хранить только префиксы с
    // последнего renew.

    ReducedCollection(BinaryOperator<T> reducer) {
        this.reducer = reducer;
    }

    @Override
    public void renew(Collection<? extends T> elements) {
        // 1 one argument reduce can't determine type of the |identity| parameter. That's why we should do it manually
        // and use 3 arguments reduce.
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
