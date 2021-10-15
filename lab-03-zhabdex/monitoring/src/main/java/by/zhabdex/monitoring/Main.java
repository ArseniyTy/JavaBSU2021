package by.zhabdex.monitoring;

import by.derovi.service_monitoring.visualizer.Table;
import by.derovi.service_monitoring.visualizer.TerminalRenderer;
import by.zhabdex.common.Service;
import by.zhabdex.common.Tools;
import com.fasterxml.jackson.core.type.TypeReference;
import lombok.SneakyThrows;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {
    public static List<String> serviceToString(Service service) {
        return List.of(
                service.getName(),
                service.getDataCenter(),
                String.valueOf(service.getAveragePing()),
                String.valueOf(service.getNodesCount()),
                String.valueOf(service.getRequestsPerSecond()),
                String.valueOf(service.getStartedTime()),
                String.valueOf(service.getCurrentTime()));
    }

    public static List<String> getHeaders() {
        return List.of(
                "Name",
                "Data center",
                "Ping",
                "Available Nodes",
                "Requests/sec",
                "Started time",
                "Current time");
    }

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        TerminalRenderer terminal = TerminalRenderer.init(1);

        Runnable myRun = () -> {
            try {
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(new URI("http://zhabdex.ovi.by/status"))
                        .GET()
                        .build();
                HttpResponse<String> response = HttpClient.newBuilder()
                        .build()
                        .send(request, HttpResponse.BodyHandlers.ofString());

                List<Service> services = Tools.JSON.readValue(response.body(), new TypeReference<>() {});
                var rows = services.stream().map(Main::serviceToString).toList();

                Table table = new Table("Zhabdex services").addRow(Main.getHeaders()).addRows(rows);
                terminal.render(List.of(table));
            } catch (Exception e) {
                e.printStackTrace();
                System.exit(-1);
            }
        };

        ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(myRun, 0, 1, TimeUnit.SECONDS);
        // not closing the app, when terminal closes
    }
}
