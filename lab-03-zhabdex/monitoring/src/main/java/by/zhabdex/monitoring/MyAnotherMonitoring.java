package by.zhabdex.monitoring;

import by.derovi.service_monitoring.visualizer.Table;
import by.zhabdex.common.Service;
import by.zhabdex.monitoring_lib.ActiveMonitoring;
import by.zhabdex.monitoring_lib.Monitoring;

import java.util.AbstractMap;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@ActiveMonitoring
public class MyAnotherMonitoring implements Monitoring {
    FinalProcessedCollection<Service, Table> collection =
            new GroupingCollection<>(Service::getDataCenter)
                    .compose(new MappedCollection<>(
                            entry -> new AbstractMap.SimpleEntry<>(
                                    entry.getKey(),
                                    entry.getValue().stream()
                                            .mapToLong(Service::getRequestsPerSecond).sum()))
                    ).compose(new TableViewCollection<>("Nodes availability", List.of(
                            TableViewCollection.ColumnProvider.of("Data center name", Map.Entry::getKey),
                            TableViewCollection.ColumnProvider.of("Available nodes", Map.Entry::getValue)))
                    );

    @Override
    public void update(Collection<? extends Service> services) {
        collection.renew(services);
    }

    @Override
    public Table getStatistics() {
        return collection.currentState();
    }
}
