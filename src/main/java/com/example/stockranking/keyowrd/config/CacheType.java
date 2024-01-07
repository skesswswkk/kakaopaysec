package com.example.stockranking.keyowrd.config;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum CacheType {
    POPULAR_KEYWORD("popularKeywords", 10, 20);

    private final String cacheName;
    private final int expireAfterWrite;
    private final int maximumSize;
}
