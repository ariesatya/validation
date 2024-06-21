package com.arie.validation.service;

import com.arie.validation.constant.Role;
import com.arie.validation.dto.SomeDTO;

public interface SomeService {
    SomeDTO createMethod(Role role, SomeDTO someDTO);
    SomeDTO getMethod(Long id);
}
