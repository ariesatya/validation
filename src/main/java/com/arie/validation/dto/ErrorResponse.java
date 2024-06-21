package com.arie.validation.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Setter
@Getter
@JsonPropertyOrder({"error", "path", "timestamp"})
public class ErrorResponse<T> {

    private T error;

    private String path;

    private final Long timestamp = System.currentTimeMillis();
}
