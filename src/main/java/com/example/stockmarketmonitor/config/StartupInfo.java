package com.example.stockmarketmonitor.config;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.time.Instant;

@Component
public class StartupInfo implements ApplicationListener<ContextRefreshedEvent> {

    private Instant startupTime;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        startupTime = Instant.now();
    }

    public Instant getStartupTime() {
        return startupTime;
    }
}
