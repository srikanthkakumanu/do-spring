package spring.boot.validation.builder;

import org.springframework.validation.Errors;
import org.springframework.validation.ObjectError;
import spring.boot.validation.ToDoValidationError;

public class ToDoValidationErrorBuilder {

    public static ToDoValidationError fromBindingErrors(Errors errors) {
        ToDoValidationError error =
                new ToDoValidationError(
                        "Validation failed. "
                                + errors.getErrorCount()
                                + " error(s)");

        for (ObjectError objectError : errors.getAllErrors()) {
            error.addValidationError(objectError.getDefaultMessage());
        }
        return error;
    }
}
