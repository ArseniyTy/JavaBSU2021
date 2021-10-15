package by.zhabdex.monitoring;

import java.util.Collection;

public interface ProcessedCollection<T, E> extends
        FinalProcessedCollection<T, Collection<? extends E>> {}
