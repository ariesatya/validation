package com.arie.validation.service.impl;

import com.arie.validation.constant.Role;
import com.arie.validation.dto.SomeDTO;
import com.arie.validation.service.SomeService;
import com.arie.validation.validation.service.SomeValidationService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@AllArgsConstructor
public class SomeServiceImpl implements SomeService {

    private final SomeValidationService someValidationService;

    @Override
    public SomeDTO createMethod(Role role, SomeDTO someDTO) {
        someValidationService.validateBusinessByRole(role, someDTO);
        // client
        // run some repository
        return someDTO;
    }

    @Override
    public SomeDTO getMethod(Long id) {
        return SomeDTO.builder()
            .id(id)
            .name("Arie")
            .address("Jakarta")
            .age(25)
            .build();
    }
}
