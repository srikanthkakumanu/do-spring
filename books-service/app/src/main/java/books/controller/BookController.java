package books.controller;

import books.model.BookDTO;
import books.service.CommonService;
import books.validation.BookValidationError;
import books.validation.builder.BookValidationErrorBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import jakarta.validation.Valid;
import java.net.URI;

@Slf4j
@RequestMapping("/api")
@RestController
public class BookController {


    private final CommonService<BookDTO> service;


    @Autowired
    public BookController(CommonService<BookDTO> bookService) {
        this.service = bookService;
    }

    @GetMapping("/books")
    public ResponseEntity<Iterable<BookDTO>> getBooks() {
        log.debug("Fetch all books");
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable Long id) {
        log.debug("Fetching Book: {}", id);
        return ResponseEntity.ok(service.findById(id));
    }

    @PatchMapping("/book/{id}/title")
    public ResponseEntity<BookDTO> setTitle(@PathVariable Long id, @RequestBody String title) {
        BookDTO findBook = service.findById(id);
        findBook.setTitle(title);
        BookDTO result = service.update(findBook);

        log.debug("Book Title Updated : {}", result.toString());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(result.getId())
                .toUri();

        return ResponseEntity
                .ok()
                .header("Location",location.toString())
                .build();
    }

    @PatchMapping("/book/{id}/isbn")
    public ResponseEntity<BookDTO> setIsbn(@PathVariable Long id, @RequestBody String isbn) {
        BookDTO findBook = service.findById(id);
        findBook.setIsbn(isbn);
        BookDTO result = service.update(findBook);

        log.debug("Book ISBN Updated : {}", result.toString());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .buildAndExpand(result.getId())
                .toUri();

        return ResponseEntity
                .ok()
                .header("Location",location.toString())
                .build();
    }

    @PatchMapping("/book/{id}/publisher")
    public ResponseEntity<BookDTO> setPublisher(@PathVariable Long id, @RequestBody String publisher) {
        BookDTO findBook = service.findById(id);
        findBook.setPublisher(publisher);
        BookDTO result = service.update(findBook);

        log.debug("Book Publisher Updated : {}", result.toString());

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
     * This annotation validates incoming data and is used as
     * a method’s parameters. To trigger a validator, it is necessary to
     * annotate the data you want to validate with @NotNull, @NotBlank,
     * and other annotations.
     * @param book book instance
     * @param errors errorMap
     * @return JSON Response Entity
     */
    @PostMapping("/book")
    public ResponseEntity<?> createBook(
            @Valid @RequestBody BookDTO book,
            Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .body(BookValidationErrorBuilder
                            .fromBindingErrors(errors));
        }

        BookDTO result = service.save(book);

        log.debug("Book Saved : {}", result.toString());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @PutMapping("/book")
    public ResponseEntity<?> updateBook(
            @Valid @RequestBody BookDTO book,
            Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .body(BookValidationErrorBuilder
                            .fromBindingErrors(errors));
        }

        BookDTO result = service.update(book);

        log.debug("Book updated : {}", result.toString());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/book/{id}")
    public ResponseEntity<BookDTO> deleteBook(@PathVariable Long id) {
        service.delete(BookDTO.builder().id(id).build());

        log.debug("Book Deleted : {}", id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/book")
    public ResponseEntity<BookDTO> deleteBook(@RequestBody BookDTO book) {

        service.delete(book);

        log.debug("Book Deleted : {}", book.toString());

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
    public BookValidationError handleException(Exception exception
            /*DataAccessException exception*/) {
        return new BookValidationError(exception.getMessage());
    }
}