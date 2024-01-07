package com.example.stockranking.stock.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
@RequiredArgsConstructor
@Schema(description = "종목코드 전체 검색 결과")
public class StockFullSearchResponse {
    private List<StockSearchResponse> datas;
}
