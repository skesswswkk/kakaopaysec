package com.example.stockranking.keyowrd.controller;

import com.example.stockranking.common.response.ApiErrorResponse;
import com.example.stockranking.keyowrd.domain.PopularKeyword;
import com.example.stockranking.keyowrd.service.PopularKeywordService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Tag(name = "popular-keywords", description = "인기 종목 API")
@RequiredArgsConstructor
@RequestMapping("api/stock")
@RestController
@ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "조회 성공"),
        @ApiResponse(responseCode = "400", description = "잘못된 요청", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class))),
        @ApiResponse(responseCode = "503", description = "서버 에러", content = @Content(schema = @Schema(implementation = ApiErrorResponse.class)))})

public class PopularKeywordController {
    private final PopularKeywordService popularKeywordService;

    @Operation(summary = "인기 종목 순으로 조회")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "요청 성공")})
    @GetMapping("/popular-keyword-rate")
    public List<PopularKeyword> getPopularSearchKeyword() {
        return popularKeywordService.getTopPopularKeywords();
    }
}
