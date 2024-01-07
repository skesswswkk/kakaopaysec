package com.example.stockranking.common.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;

@Schema(description = "에러 응답")
@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ApiErrorResponse {
    @Schema(description = "발생시간")
    private final LocalDateTime timestamp = LocalDateTime.now();
    @Schema(description = "상태코드")
    private final HttpStatus httpStatus;
    @Schema(description = "에러내용")
    private final String message;

    public static ResponseEntity<ApiErrorResponse> toResponseEntity(HttpStatus httpStatus, String message) {
        return ResponseEntity
                .status(httpStatus)
                .body(new ApiErrorResponse(httpStatus, message));
    }
}
