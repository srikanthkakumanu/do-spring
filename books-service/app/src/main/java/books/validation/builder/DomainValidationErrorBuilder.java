package books.validation.builder;

import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import books.validation.DomainValidationError;

public class DomainValidationErrorBuilder {

    public static DomainValidationError fromBindingErrors(Errors errors) {
        DomainValidationError error =
                new DomainValidationError(
                        "Validation failed. "
                                + errors.getErrorCount()
                                + " error(s)");

        for (ObjectError objectError : errors.getAllErrors()) {
            error.addValidationError(objectError.getDefaultMessage());
        }
        return error;
    }
}
