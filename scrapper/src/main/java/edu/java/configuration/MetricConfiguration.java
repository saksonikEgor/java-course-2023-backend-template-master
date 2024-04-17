package edu.java.configuration;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.binder.MeterBinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MetricConfiguration {
    @Bean
    public MeterBinder meterRegistry() {
        return meterRegistry -> Counter.builder("proceeded_request_count")
            .description("Количество обработанных сообщений")
            .register(meterRegistry);
    }
}
