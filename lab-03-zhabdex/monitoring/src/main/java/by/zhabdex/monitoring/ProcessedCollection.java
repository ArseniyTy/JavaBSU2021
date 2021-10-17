package by.zhabdex.monitoring;

import java.util.Collection;

public interface ProcessedCollection<T, E> extends
        FinalProcessedCollection<T, Collection<? extends E>> {

    default <U> ProcessedCollection<T, U> compose(ProcessedCollection<E, U> rhs) {
        var lhs = this;
        return new ProcessedCollection<>() {
            @Override
            public void renew(Collection<? extends T> elements) {
                lhs.renew(elements);
                rhs.renew(lhs.currentState());
            }

            @Override
            public Collection<? extends U> currentState() {
                return rhs.currentState();
            }
        };
    }

    default <U> FinalProcessedCollection<T, U> compose(FinalProcessedCollection<E, U> rhs) {
        var lhs = this;
        return new FinalProcessedCollection<>() {
            @Override
            public void renew(Collection<? extends T> elements) {
                lhs.renew(elements);
                rhs.renew(lhs.currentState());
            }

            @Override
            public U currentState() {
                return rhs.currentState();
            }
        };
    }
}
