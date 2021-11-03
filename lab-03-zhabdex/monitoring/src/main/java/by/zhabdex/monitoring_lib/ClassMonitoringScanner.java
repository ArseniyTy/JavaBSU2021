package by.zhabdex.monitoring_lib;

import org.reflections.Reflections;

import java.util.Collection;

public class ClassMonitoringScanner implements MonitoringScanner {
    public ClassMonitoringScanner() {}

    @Override
    public Collection<Monitoring> scan(Reflections reflection) {
        var annotatedWithClasses = reflection.getTypesAnnotatedWith(ActiveMonitoring.class);
        var implementsClasses = reflection.getSubTypesOf(Monitoring.class);
        annotatedWithClasses.retainAll(implementsClasses);
        var result = annotatedWithClasses.stream().toList();

        return result.stream().map(monitoring -> {
            try {
                return (Monitoring) monitoring.getConstructor().newInstance();
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(1);
                return null;
            }
        }).toList();
    }
}
