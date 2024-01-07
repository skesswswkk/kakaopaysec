package com.example.stockranking.keyowrd.domain;

import com.example.stockranking.keyowrd.entity.KeywordCount;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "인기 종목")
public record PopularKeyword(
        @Schema(description = "검색 종목") String keyword,
        @Schema(description = "검색된 횟수") Long searchCount) {
    public static PopularKeyword of(KeywordCount keywordCount) {
        return new PopularKeyword(keywordCount.getKeyword(), keywordCount.getCount());
    }
}
