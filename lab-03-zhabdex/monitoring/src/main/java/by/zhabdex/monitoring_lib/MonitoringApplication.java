package by.zhabdex.monitoring_lib;

import by.derovi.service_monitoring.visualizer.TerminalRenderer;
import by.zhabdex.common.Service;
import by.zhabdex.common.Tools;
import com.fasterxml.jackson.core.type.TypeReference;
import org.reflections.Reflections;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class MonitoringApplication {
    private String packageName;
    private String serviceURL;
    private List<MonitoringScanner> scanners;
    private Collection<Monitoring> monitorings;

    public MonitoringApplication(String packageName, String serviceURL,
                                 List<MonitoringScanner> scanners,
                                 Collection<Monitoring> monitorings)
            throws IOException, URISyntaxException, InterruptedException {
        this.packageName = packageName;
        this.serviceURL = serviceURL;
        this.scanners = scanners;
        this.monitorings = monitorings;
        update();
    }

    public static MonitoringApplicationBuilder builder() {
        return new MonitoringApplicationBuilder();
    }

    public static class MonitoringApplicationBuilder {
        private String packageName;
        private String serviceURL;
        private List<MonitoringScanner> scanners = new ArrayList<>();
        private List<Monitoring> monitorings = new ArrayList<>();

        MonitoringApplicationBuilder() {}

        public MonitoringApplicationBuilder packageName(String packageName) {
            this.packageName = packageName;
            return this;
        }

        public MonitoringApplicationBuilder serviceURL(String serviceURL) {
            this.serviceURL = serviceURL;
            return this;
        }

        public MonitoringApplicationBuilder addScanner(MonitoringScanner scanner) {
            scanners.add(scanner);
            return this;
        }

        public MonitoringApplication start() throws IOException, URISyntaxException, InterruptedException {
            scanners.forEach(scanner ->
                    monitorings.addAll(
                            scanner.scan(new Reflections(packageName))));
            return new MonitoringApplication(packageName, serviceURL, scanners, monitorings);
        }
    }

    private List<Service> fetchServices() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI(serviceURL))
                .GET()
                .build();
        HttpResponse<String> response = HttpClient.newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());
        return Tools.JSON.readValue(response.body(), new TypeReference<>() {});
    }

    private void update() throws IOException, URISyntaxException, InterruptedException {
        TerminalRenderer terminal = TerminalRenderer.init(monitorings.size());

        // not closing the app, when terminal closes
        while (true) {
            var services = fetchServices();
            monitorings.forEach(monitoring -> monitoring.update(services));
            terminal.render(monitorings.stream().map(Monitoring::getStatistics).toList());
            Thread.sleep(1000);
        }
    }
}
