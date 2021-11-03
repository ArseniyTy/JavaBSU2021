package by.zhabdex.monitoring;

import by.derovi.service_monitoring.visualizer.Table;
import by.zhabdex.common.Service;
import by.zhabdex.monitoring_lib.ActiveMonitoring;
import by.zhabdex.monitoring_lib.MonitoringContainer;

import java.util.List;

@MonitoringContainer
public class MyMonitoringContainer {
    @ActiveMonitoring
    static FinalProcessedCollection<Service, Table> top2Nodes() {
        return new SortedCollection<>(Service::getNodesCount)
                .compose(new LimitedCollection<>(2))
                .compose(new TableViewCollection<>("top nodes", List.of(
                        TableViewCollection.ColumnProvider.of("Name", Service::getName),
                        TableViewCollection.ColumnProvider.of("Available nodes", Service::getNodesCount)
                )));
    }

    @ActiveMonitoring
    static FinalProcessedCollection<Service, Table> top3Nodes() {
        return new SortedCollection<>(Service::getNodesCount)
                .compose(new LimitedCollection<>(3))
                .compose(new TableViewCollection<>("top nodes", List.of(
                        TableViewCollection.ColumnProvider.of("Name", Service::getName),
                        TableViewCollection.ColumnProvider.of("Available nodes", Service::getNodesCount)
                )));
    }
}
