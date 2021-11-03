package by.zhabdex.monitoring;

import java.util.*;
import java.util.function.Function;

public class GroupingCollection<T, K>
        implements ProcessedCollection<T, Map.Entry<? extends K, ? extends List<? extends T>>> {
    private final Function<? super T, ? extends K> classifier;
    private final HashMap<K, List<T>> keys_with_elements = new HashMap<>();

    public GroupingCollection(Function<? super T, ? extends K> classifier) {
        this.classifier = classifier;
    }

    @Override
    public void renew(Collection<? extends T> elements) {
        keys_with_elements.clear();
        elements.forEach(e -> {
            var key = classifier.apply(e);
            keys_with_elements.putIfAbsent(key, new ArrayList<>());
            keys_with_elements.get(key).add(e);
        });
    }

    @Override
    public Collection<? extends Map.Entry<? extends K, ? extends List<? extends T>>> currentState() {
        return keys_with_elements.entrySet();
    }

}
