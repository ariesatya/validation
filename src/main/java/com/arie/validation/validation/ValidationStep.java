package com.arie.validation.validation;

public abstract class ValidationStep<T> {

    private ValidationStep<T> next;

    public ValidationStep<T> linkWith(ValidationStep<T> next) {
        if (this.next == null) {
            this.next = next;
        } else {
            ValidationStep<T> lastStep = this.next;
            while (lastStep.next != null) {
                lastStep = lastStep.next;
            }
            lastStep.next = next;
        }
        return this;
    }

    public abstract ValidationResult validate(T toValidate);

    protected ValidationResult checkNext(T toValidate) {
        return (next == null) ? ValidationResult.valid() : next.validate(toValidate);
    }
}
