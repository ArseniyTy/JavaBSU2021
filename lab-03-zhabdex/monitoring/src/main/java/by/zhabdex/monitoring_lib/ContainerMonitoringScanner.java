package by.zhabdex.monitoring_lib;

import by.derovi.service_monitoring.visualizer.Table;
import by.zhabdex.common.Service;
import by.zhabdex.monitoring.FinalProcessedCollection;
import org.reflections.Reflections;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.Collection;

public class ContainerMonitoringScanner implements MonitoringScanner {
    @SuppressWarnings("unchecked")  // for |method.invoke()| cast
    @Override
    public Collection<Monitoring> scan(Reflections reflection) {
        return reflection.getTypesAnnotatedWith(MonitoringContainer.class)
                .stream()
                .map(Class::getDeclaredMethods)  // including not only public
                .flatMap(Arrays::stream)  // flatten the Stream<Methods[]> to Stream<Method>
                .filter(method -> method.isAnnotationPresent(ActiveMonitoring.class))
                .peek(method -> method.setAccessible(true))  // as |foreach|, but returns |stream()|
                .map(method -> {
                    try {
                        return (FinalProcessedCollection<Service, Table>) method.invoke(null);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.exit(1);
                        return null;
                    }
                })
                .map(collection -> new Monitoring() {
                    @Override
                    public void update(Collection<? extends Service> services) {
                        collection.renew(services);
                    }

                    @Override
                    public Table getStatistics() {
                        return collection.currentState();
                    }
                })
                .map(monitoring -> (Monitoring) monitoring)
                .toList();
    }
}
