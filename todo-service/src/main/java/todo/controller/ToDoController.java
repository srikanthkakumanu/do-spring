package todo.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import todo.builder.ToDoBuilder;
import todo.domain.ToDo;
import todo.repository.CommonRepository;
import todo.validation.ToDoValidationError;
import todo.validation.builder.ToDoValidationErrorBuilder;

import java.net.URI;

@RestController
@RequestMapping("/api")
public class ToDoController {

    private CommonRepository<ToDo> repository;

    /**
     * This @Autowired annotation can be omitted;
     * Spring automatically injects any declared
     * dependency since version 4.3.
     * @param repository ToDo repository
     */
    @Autowired
    public ToDoController(CommonRepository<ToDo> repository) {
        this.repository = repository;
    }

    @GetMapping("/todo")
    public ResponseEntity<Iterable<ToDo>> getToDos() {
        return ResponseEntity.ok(repository.findAll());
    }

    @GetMapping("/todo/{id}")
    public ResponseEntity<ToDo> getToDoById(@PathVariable String id) {
        return ResponseEntity.ok(repository.findById(id));
    }

    @PatchMapping("/todo/{id}")
    public ResponseEntity<ToDo> setCompleted(@PathVariable String id) {
        ToDo result = repository.findById(id);
        result.setCompleted(true);
        repository.save(result);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(result.getId())
                .toUri();

        return ResponseEntity
                .ok()
                .header("Location",location.toString())
                .build();
    }

    /**
     * @Valid. This annotation validates incoming data and is used as
     * a method’s parameters. To trigger a validator, it is necessary to
     * annotate the data you want to validate with @NotNull, @NotBlank,
     * and other annotations.
     * @param toDo todo task
     * @param errors errorMap
     * @return JSON Response Entity
     */
    @RequestMapping(value="/todo",
            method = {RequestMethod.POST,RequestMethod.PUT})
    public ResponseEntity<?> createToDo(
            @Valid @RequestBody ToDo toDo,
            Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .body(ToDoValidationErrorBuilder
                            .fromBindingErrors(errors));
        }

        ToDo result = repository.save(toDo);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/todo/{id}")
    public ResponseEntity<ToDo> deleteToDo(@PathVariable String id) {

        repository.delete(ToDoBuilder.create().withId(id).build());
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/todo")
    public ResponseEntity<ToDo> deleteToDo(@RequestBody ToDo toDo) {

        repository.delete(toDo);
        return ResponseEntity.noContent().build();
    }

    /**
     * @ResponseStatus - Normally, this annotation is used when a method
     * has a void return type (or null return value). This annotation sends
     * back the HTTP status code specified in the response.
     *
     * @ExceptionHandler. The Spring MVC automatically declares built-in
     * resolvers for exceptions and adds the support to this annotation. In
     * this case, the @ExceptionHandler is declared inside this controller
     * class (or you can use it within a @ControllerAdvice interceptor)
     * and any exception is redirected to the handleException method.
     *
     * You can be more specific if needed. For example, you can have a
     * DataAccessException and handle through a method.
     * @param exception exception
     * @return
     */
    @ExceptionHandler
    @ResponseStatus(value = HttpStatus.BAD_REQUEST)
    public ToDoValidationError handleException(Exception exception
            /*DataAccessException exception*/) {
        return new ToDoValidationError(exception.getMessage());
    }
}
