package books.controller;

import books.model.AuthorDTO;
import books.model.SortOrder;
import books.service.AuthorService;
import books.validation.DomainValidationError;
import books.validation.builder.DomainValidationErrorBuilder;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@Slf4j
@RequestMapping("/api/authors")
@RestController
public class AuthorController {

    private final AuthorService<AuthorDTO> service;

    private AuthorController(AuthorService<AuthorDTO> service) {
        this.service = service;
    }

    @GetMapping
    private ResponseEntity<Iterable<AuthorDTO>> getAuthors(
            @RequestParam(defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(defaultValue = "0", required = false) Integer pageSize,
            @RequestParam(defaultValue = "false") Boolean paged) {

        log.debug("Fetch all Authors: [pageNumber: {}, pageSize: {}, paged: {}]", pageNumber, pageSize, paged);

        return ResponseEntity.ok((!paged)
                ? service.findAll()
                : service.findAll(pageNumber, pageSize));
    }

    @GetMapping("/{id}")
    private ResponseEntity<AuthorDTO> getAuthorById(@PathVariable UUID id) {
        log.debug("Fetch Author: [Id: {}]", id);
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/firstName")
    private ResponseEntity<Iterable<AuthorDTO>> getAuthorsByFirstName(
            @RequestParam String firstName,
            @RequestParam(defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(defaultValue = "0", required = false) Integer pageSize,
            @RequestParam(defaultValue = "false") Boolean paged,
            @RequestParam(defaultValue = "false") Boolean sorted,
            @RequestParam(defaultValue = "ASC") SortOrder sortOrder) {

        log.debug("Fetch all Authors: [firstName: {}, pageNumber: {}, pageSize: {}, paged: {}, sorted: {}, sortOrder: {}]", firstName, pageNumber, pageSize, paged, sorted, sortOrder);

        return ResponseEntity.ok((!paged)
                ? service.findByFirstName(firstName)
                : service.findByFirstName(firstName, pageNumber, pageSize, sorted, sortOrder));
    }

    @GetMapping("/lastName")
    private ResponseEntity<Iterable<AuthorDTO>> getAuthorsByLastName(
            @RequestParam
            String lastName,
            @RequestParam(defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(defaultValue = "0", required = false) Integer pageSize,
            @RequestParam(defaultValue = "false") Boolean paged,
            @RequestParam(defaultValue = "false") Boolean sorted,
            @RequestParam(defaultValue = "ASC") SortOrder sortOrder) {


        log.debug("Fetch all Authors: [lastName: {}, pageNumber: {}, pageSize: {}, paged: {}, sorted: {}, sortOrder: {}]", lastName, pageNumber, pageSize, paged, sorted, sortOrder);

        return ResponseEntity.ok((!paged)
                ? service.findByLastName(lastName)
                : service.findByLastName(lastName, pageNumber, pageSize, sorted, sortOrder));
    }

    @GetMapping("/name")
    private ResponseEntity<Iterable<AuthorDTO>> getAuthorsByFirstNameAndLastName(
            @RequestParam String firstName, @RequestParam String lastName) {

        log.debug("Fetch all authors: [firstName: {}, lastName: {}]", firstName, lastName);
        return ResponseEntity.ok(service.findByFirstNameAndLastName(firstName, lastName));
    }

    @GetMapping("/genre")
    private ResponseEntity<Iterable<AuthorDTO>> getAuthorsByGenre(
            @RequestParam String genre,
            @RequestParam(defaultValue = "0", required = false) Integer pageNumber,
            @RequestParam(defaultValue = "0", required = false) Integer pageSize,
            @RequestParam(defaultValue = "false") Boolean paged,
            @RequestParam(defaultValue = "false") Boolean sorted,
            @RequestParam(defaultValue = "ASC") SortOrder sortOrder) {

        log.debug("Fetch all Authors - [genre: {}, pageNumber: {}, pageSize: {}, paged: {}, sorted: {}, sortOrder: {}]", genre, pageNumber, pageSize, paged, sorted, sortOrder);

        return ResponseEntity.ok((!paged)
                ? service.findByGenre(genre)
                : service.findByGenre(genre, pageNumber, pageSize, sorted, sortOrder));
    }


    /**
     * This annotation validates incoming data and is used as
     * a method’s parameters. To trigger a validator, it is necessary to
     * annotate the data you want to validate with @NotNull, @NotBlank,
     * and other annotations.
     * @param author author instance
     * @param errors errorMap
     * @return JSON Response Entity
     */
    @RequestMapping(method = {RequestMethod.POST,RequestMethod.PUT})
    private ResponseEntity<?> saveAuthor(
            @Valid @RequestBody AuthorDTO author,
            Errors errors) {

        log.debug("Save Author: [{}]", author.toString());

        if (errors.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .body(DomainValidationErrorBuilder
                            .fromBindingErrors(errors));
        }

        AuthorDTO result = service.save(author);

        log.debug("Author Saved: [{}]", result.toString());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<AuthorDTO> deleteAuthor(@PathVariable UUID id) {

        log.debug("Delete Author: [{}]", id.toString());

        AuthorDTO deleted = service.delete(AuthorDTO.builder().id(id).build());

        log.debug("Author Deleted: [{}]", deleted);

        return ResponseEntity.ok(deleted);
    }

    @DeleteMapping
    private ResponseEntity<AuthorDTO> deleteAuthor(@RequestBody AuthorDTO author) {

        log.debug("Delete Author: [{}]", author.toString());

        AuthorDTO deleted = service.delete(author);

        log.debug("Author Deleted: [{}]", deleted);

        return ResponseEntity.ok(deleted);
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
    private DomainValidationError handleException(Exception exception
            /*DataAccessException exception*/) {
        return new DomainValidationError(exception.getMessage());
    }

//    @PatchMapping("/{id}/firstName")
//    private ResponseEntity<AuthorDTO> setFirstName(@PathVariable UUID id,
//                                                   @RequestBody String firstName) {
//        log.debug("Update Author[firstName: {}]", firstName);
//        AuthorDTO found = service.findById(id);
//        found.setFirstName(firstName);
//        AuthorDTO result = service.save(found);
//
//        log.debug("Author {} Updated.", result.toString());
//
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .buildAndExpand(result.getId())
//                .toUri();
//
//        return ResponseEntity
//                .ok()
//                .header("Location",location.toString())
//                .build();
//    }
//
//    @PatchMapping("/{id}/lastName")
//    private ResponseEntity<AuthorDTO> setLastName(@PathVariable UUID id,
//                                                  @RequestBody String lastName) {
//        log.debug("Update Author[lastName: {}]", lastName);
//        AuthorDTO found = service.findById(id);
//        found.setLastName(lastName);
//        AuthorDTO result = service.save(found);
//
//        log.debug("Author {} Updated.", result.toString());
//
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .buildAndExpand(result.getId())
//                .toUri();
//
//        return ResponseEntity
//                .ok()
//                .header("Location",location.toString())
//                .build();
//    }
//
//    @PatchMapping("/{id}/genre")
//    private ResponseEntity<AuthorDTO> setGenre(@PathVariable UUID id,
//                                               @RequestBody String genre) {
//        log.debug("Update Author[genre: {}]", genre);
//        AuthorDTO found = service.findById(id);
//        found.setGenre(genre);
//        AuthorDTO result = service.save(found);
//
//        log.debug("Author {} Updated.", result.toString());
//
//        URI location = ServletUriComponentsBuilder
//                .fromCurrentRequest()
//                .buildAndExpand(result.getId())
//                .toUri();
//
//        return ResponseEntity
//                .ok()
//                .header("Location",location.toString())
//                .build();
//    }
}
