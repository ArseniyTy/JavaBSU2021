package by.zhabdex.monitoring;

import by.zhabdex.monitoring_lib.ClassMonitoringScanner;
import by.zhabdex.monitoring_lib.MonitoringApplication;

import java.io.IOException;
import java.net.URISyntaxException;

public class Main {
    public static void main(String[] args) throws URISyntaxException, IOException, InterruptedException {
        MonitoringApplication
                .builder()
                .packageName("by.zhabdex.monitoring")
                .serviceURL("http://zhabdex.ovi.by/status")
                .addScanner(new ClassMonitoringScanner())
//                .addScanner(new ContainerMonitoringScanner())
                .start();
    }
}
