package by.zhabdex.monitoring;

import by.derovi.service_monitoring.visualizer.TerminalRenderer;
import by.zhabdex.common.Service;
import by.zhabdex.common.Tools;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class Main {

    public static List<Service> fetchServices() throws URISyntaxException, IOException, InterruptedException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(new URI("http://zhabdex.ovi.by/status"))
                .GET()
                .build();
        HttpResponse<String> response = HttpClient.newBuilder()
                .build()
                .send(request, HttpResponse.BodyHandlers.ofString());
        return Tools.JSON.readValue(response.body(), new TypeReference<>() {});
    }

    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        TerminalRenderer terminal = TerminalRenderer.init(1);

        var collection =
                new SortedCollection<>(Service::getRequestsForUptime).compose(
                        new TableViewCollection<>("Test", List.of(
                                TableViewCollection.ColumnProvider.of("Name", Service::getName),
                                TableViewCollection.ColumnProvider.of("Data center", Service::getDataCenter),
                                TableViewCollection.ColumnProvider.of("Ping", Service::getAveragePing),
                                TableViewCollection.ColumnProvider.of("Available nodes", Service::getNodesCount),
                                TableViewCollection.ColumnProvider.of("Requests/sec", Service::getRequestsPerSecond),
                                TableViewCollection.ColumnProvider.of("Started time", Service::getStartedTime),
                                TableViewCollection.ColumnProvider.of("Current time", Service::getCurrentTime)
                        ))
                );

        // not closing the app, when terminal closes
        while (true) {
            collection.renew(fetchServices());
            terminal.render(List.of(collection.currentState()));
            Thread.sleep(1000);
        }
    }
}
