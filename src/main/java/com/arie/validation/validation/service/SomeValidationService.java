package com.arie.validation.validation.service;

import com.arie.validation.constant.Role;
import com.arie.validation.dto.SomeDTO;
import com.arie.validation.validation.ValidationResult;
import com.arie.validation.validation.ValidationStep;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
@AllArgsConstructor
@SuppressWarnings("unused")
public class SomeValidationService {

    private final Validator validator;

    /**
     * Validate business rule by role
     * 1. validate parameter SomeDTO
     * 2. validate user age by role
     * 3. validate user address
     */
    public void validateBusinessByRole(Role role, SomeDTO someDTO) {
        new ValidateParameter(validator)
            .linkWith(new ValidateUserAge(role))
            .linkWith(new ValidateUserAddress())
            .validate(someDTO);
    }

    /**
     * Validate parameter SomeDTO
     */
    @AllArgsConstructor
    private static class ValidateParameter extends ValidationStep<SomeDTO> {

        private final Validator validator;

        @Override
        public ValidationResult validate(SomeDTO toValidate) {
            Set<ConstraintViolation<SomeDTO>> violations = validator.validate(toValidate);
            if (!violations.isEmpty()) {
                Map<String, String> errors = new HashMap<>();
                for (ConstraintViolation<SomeDTO> violation : violations) {
                    String field = violation.getPropertyPath().toString();
                    String message = violation.getMessage();
                    errors.put(field, message);
                }
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, errors.toString());
            }
            return checkNext(toValidate);
        }

    }

    /**
     * validate user age by role
     * 1. Admin age must be greater than 40
     * 2. Supervisor age must be greater than 30
     * 3. User age must be greater than 20
     */
    @AllArgsConstructor
    private static class ValidateUserAge extends ValidationStep<SomeDTO> {

        private final Role role;

        @Override
        public ValidationResult validate(SomeDTO toValidate) {
            if (role.equals(Role.ADMIN) && toValidate.getAge() < 40) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Admin age must be greater than 10");
            } else if (role.equals(Role.SUPERVISOR) && toValidate.getAge() < 30) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Supervisor age must be greater than 30");
            } else if (role.equals(Role.USER) && toValidate.getAge() < 20) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User age must be greater than 20");
            }
            return checkNext(toValidate);
        }
    }

    /**
     * Validate user address
     * 1. validate address to accept only address that start with Jl.
     */
    @AllArgsConstructor
    private static class ValidateUserAddress extends ValidationStep<SomeDTO> {

        @Override
        public ValidationResult validate(SomeDTO toValidate) {
            if (!toValidate.getAddress().startsWith("Jl.")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Address must start with Jl.");
            }
            return checkNext(toValidate);
        }
    }


}
