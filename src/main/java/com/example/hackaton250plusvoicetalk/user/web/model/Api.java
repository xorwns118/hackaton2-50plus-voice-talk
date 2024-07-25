package com.example.hackaton250plusvoicetalk.user.web.model;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class Api<T>{
    private String resultCode;
    private String resultMessage;

    @Valid
    private T data;
    private Error error;

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public static class Error {
        private List<String> errorMessage;
    }

    public static <T> Api<T> buildApiResponse(HttpStatus status, T data) {
        return Api.<T>builder()
                .data(data)
                .resultCode(String.valueOf(status.value()))
                .resultMessage(status.getReasonPhrase())
                .build();
    }
}
