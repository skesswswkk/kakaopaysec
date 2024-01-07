package com.example.stockranking.stock.controller;

import com.example.stockranking.common.response.ApiErrorResponse;
import com.example.stockranking.stock.domain.StockSearchResponse;
import com.example.stockranking.stock.service.StockService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "stock", description = "stock 조회 API")
@RequiredArgsConstructor
@RequestMapping("/api/stock")
@RestController
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "조회 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
        @ApiResponse(responseCode = "503", description = "서버 에러", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))})
public class StockController {
    private final StockService stockService;

    @Operation(summary = "단일 종목 조회")
    @GetMapping("/search/{stockCode}")
    public StockSearchResponse getStockSearch(@PathVariable String stockCode) {
        return stockService.searchStock(stockCode);
    }

    @Operation(summary = "상승률 순으로 조회")
    @GetMapping("/increase-rate")
    public ResponseEntity<List<StockSearchResponse>> getStocksIncreaseRate(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(stockService.IncreaseRateService(page, size));
    }

    @Operation(summary = "하락률 순으로 조회")
    @GetMapping("/decrease-rate")
    public ResponseEntity<List<StockSearchResponse>> getStocksDecreaseRate(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(stockService.DecreaseRateService(page, size));
    }

    @Operation(summary = "거래량 순으로 조회")
    @GetMapping("/fluctuation-rate")
    public ResponseEntity<List<StockSearchResponse>> getStocksFluctuationRate(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(stockService.FluctuationRateService(page, size));
    }

    @Operation(summary = "인기, 상승률, 하락률, 거래량 랜덤 순위 조회")
    @GetMapping("/random-test")
    public ResponseEntity<?> RandomTest(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        return ResponseEntity.ok(stockService.RandomTest(page, size));
    }
}
