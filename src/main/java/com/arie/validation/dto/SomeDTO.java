package com.arie.validation.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SomeDTO {
    @Schema(hidden = true)
    long id;

    @NotEmpty(message = "Name must not be empty")
    @Size(min = 5, max = 100)
    String name;

    @NotEmpty(message = "Address must not be empty")
    String address;

    @NotNull(message = "Age must not be null")
    Integer age;
}
