package com.arie.validation.validation;

public record ValidationResult(boolean isValid, String errorMessage) {
  public static ValidationResult valid() {
    return new ValidationResult(true, null);
  }

  public static ValidationResult invalid(String errorMsg) {
    return new ValidationResult(false, errorMsg);
  }

  public boolean notValid() {
    return !isValid;
  }
}
