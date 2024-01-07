package com.example.stockranking.keyowrd.event;

import com.google.common.util.concurrent.AtomicLongMap;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SearchEventHandler {
    private final AtomicLongMap<String> keywordCountMap;

    @Async("keywordCountTaskExecutor")
    @EventListener
    public void putKeywordInQueue(SearchedKeywordEvent searchedKeywordEvent) {
        keywordCountMap.incrementAndGet(searchedKeywordEvent.keyword());
    }
}
