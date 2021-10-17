package by.zhabdex.monitoring;

import by.derovi.service_monitoring.visualizer.Table;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;

public class TableViewCollection<T> implements FinalProcessedCollection<T, Table> {
    public static class ColumnProvider<K> {
        private final String title;
        private final Function<K, String> stringDataExtractor;

        private <E> ColumnProvider(String title, Function<K, E> fieldExtractor) {
            this.title = title;
            stringDataExtractor = (K value) -> String.valueOf(fieldExtractor.apply(value));
        }

        private String getTitle() {
            return title;
        }

        private String getData(K value) {
            return stringDataExtractor.apply(value);
        }

        public static <K, E> ColumnProvider<K> of(String title, Function<K, E> fieldExtractor) {
            return new ColumnProvider<>(title, fieldExtractor);
        }
    }

    private final String tableName;
    private final List<ColumnProvider<T>> columnProviders;
    private final List<String> tableHeaders;
    private Table table;

    public TableViewCollection(String tableName, List<ColumnProvider<T>> columnProviders) {
        this.tableName = tableName;
        this.columnProviders = columnProviders;
        tableHeaders = columnProviders.stream().map(ColumnProvider::getTitle).toList();
        table = new Table(tableName).addRow(tableHeaders);
    }

    @Override
    public void renew(Collection<? extends T> elements) {
        table = new Table(tableName).addRow(tableHeaders);
        elements.stream().
                map(element -> columnProviders.stream().
                        map(provider -> provider.getData(element)).toList()).
                forEach(table::addRow);
    }

    @Override
    public Table currentState() {
        return table;
    }
}
