package com.arie.validation.dto;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({ "status", "message", "data" })
public class BaseResponse<T> {

  @Schema(example = "OK")
  private HttpStatus status;

  @Schema(example = "SUCCESS")
  private String message;

  private T data;

  public BaseResponse(T data) {
    this.data = data;
  }

  public BaseResponse<T> success() {
    return this.success(HttpStatus.OK);
  }

  public BaseResponse<T> success(HttpStatus httpStatus) {
    this.status = httpStatus;
    this.message = "SUCCESS";
    return this;
  }

    public BaseResponse<T> badRequest(String message) {
        this.status = HttpStatus.BAD_REQUEST;
        this.message = message;
        return this;
    }
}
