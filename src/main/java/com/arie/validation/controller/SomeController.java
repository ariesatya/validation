package com.arie.validation.controller;

import com.arie.validation.constant.Role;
import com.arie.validation.dto.BaseResponse;
import com.arie.validation.dto.SomeDTO;
import com.arie.validation.service.SomeService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@RestController
@AllArgsConstructor
@SuppressWarnings({"unused", "generic-method"})
public class SomeController {

    private final SomeService someService;

    @GetMapping("/v1/get")
    public ResponseEntity<BaseResponse<SomeDTO>> getSome(
        @Valid @RequestParam(value = "id", defaultValue = "") @NotNull @Min(10) Long id,
        @Valid @RequestParam(value = "name", defaultValue = "") @NotEmpty String name
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(
            new BaseResponse<>(someService.getMethod(id)).success());
    }

    @PostMapping("/v1/create/binding")
    public ResponseEntity<BaseResponse<SomeDTO>> createBindingSome(
        @RequestBody @Valid SomeDTO someDTO,
        BindingResult bindingResult) {
        Map<String, String> errors = new HashMap<>();
        if (bindingResult.hasErrors()) {
            bindingResult.getAllErrors().forEach(
                error -> {
                    String fieldName = ((FieldError) error).getField();
                    String errorMessage = error.getDefaultMessage();
                    errors.put(fieldName, errorMessage);
                }
            );
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errors.toString());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(
            new BaseResponse<>(someService.createMethod(Role.USER, someDTO))
                .success(HttpStatus.CREATED));
    }

    @PostMapping("/v1/create")
    public ResponseEntity<BaseResponse<SomeDTO>> createSome(@RequestBody @Valid SomeDTO someDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            new BaseResponse<>(
                someService.createMethod(Role.USER, someDTO))
                .success(HttpStatus.CREATED));
    }

    @PostMapping("/v1/create/service")
    public ResponseEntity<BaseResponse<SomeDTO>> createSomeService(@RequestBody @Validated SomeDTO someDTO) {
        return ResponseEntity.status(HttpStatus.CREATED).body(
            new BaseResponse<>(
                someService.createMethod(Role.USER, SomeDTO.builder()
                    .age(0)
                    .address(someDTO.getAddress())
                    .build()))
                .success(HttpStatus.CREATED));
    }
}
