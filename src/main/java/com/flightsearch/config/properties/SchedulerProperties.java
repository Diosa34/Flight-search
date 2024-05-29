package com.flightsearch.config.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@Data
@ConfigurationPropertiesScan
@ConfigurationProperties(prefix = "application.scheduler")
public class SchedulerProperties {
    // Отправка первого уведомления за ... дней до дедлайна
    private long firstNotification = 3;
    // Отправка второго уведомления за ... дней до дедлайна
    private long secondNotification = 1;
}
