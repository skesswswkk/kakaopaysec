package com.example.stockranking.keyowrd.config;

import com.google.common.util.concurrent.AtomicLongMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AtomicLongMapConfig {
    @Bean
    public AtomicLongMap<String> keywordsMap() {
        return AtomicLongMap.create();
    }
}
