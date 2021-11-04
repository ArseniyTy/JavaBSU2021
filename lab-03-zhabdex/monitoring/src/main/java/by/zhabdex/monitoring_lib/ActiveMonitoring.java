package by.zhabdex.monitoring_lib;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)  // otherwise, |ContainerMonitoringScanner| can't find methods with this annotation
public @interface ActiveMonitoring { }
