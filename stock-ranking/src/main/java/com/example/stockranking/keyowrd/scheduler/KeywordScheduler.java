package com.example.stockranking.keyowrd.scheduler;

import com.example.stockranking.keyowrd.service.PopularKeywordService;
import com.google.common.util.concurrent.AtomicLongMap;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
@EnableScheduling
@Component
public class KeywordScheduler {
    private final PopularKeywordService keywordService;
    private final AtomicLongMap<String> keywordsMap;

    @Scheduled(fixedDelay = 3000)
    public void scheduleFixedDelayTask() {
        Map<String, Long> keywordMap = new HashMap<>();

        for (String keyword : keywordsMap.asMap().keySet()) {
            long count = keywordsMap.remove(keyword);

            keywordMap.put(keyword, count);
        }

        if (!keywordMap.isEmpty()) {
            keywordService.upsertKeywordCounts(keywordMap);
        }
    }
}
