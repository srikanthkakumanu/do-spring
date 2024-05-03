package books.controller;

import books.model.BookDTO;
import books.service.BookService;
import books.validation.DomainValidationError;
import books.validation.builder.DomainValidationErrorBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import jakarta.validation.Valid;
import java.net.URI;
import java.util.UUID;

@Slf4j
@RequestMapping("/api")
@RestController
public class BookController {


    private final BookService<BookDTO> service;


    @Autowired
    public BookController(BookService<BookDTO> bookService) {
        this.service = bookService;
    }

    @GetMapping("/books")
    public ResponseEntity<Iterable<BookDTO>> getBooks() {
        log.debug("Fetch all books");
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/book/{id}")
    public ResponseEntity<BookDTO> getBookById(@PathVariable UUID id) {
        log.debug("Fetching Book: {}", id);
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/book/title/{title}")
    public ResponseEntity<Iterable<BookDTO>> getBooksByTitle(@PathVariable String title) {
        log.debug("Fetch all books by Title");
        return ResponseEntity.ok(service.findByTitle(title));
    }

    @GetMapping("/book/isbn/{isbn}")
    public ResponseEntity<Iterable<BookDTO>> getBooksByIsbn(@PathVariable String isbn) {
        log.debug("Fetch all books by ISBN");
        return ResponseEntity.ok(service.findByIsbn(isbn));
    }

    @GetMapping("/book/publisher/{publisher}")
    public ResponseEntity<Iterable<BookDTO>> getBooksByPublisher(@PathVariable String publisher) {
        log.debug("Fetch all books by Publisher");
        return ResponseEntity.ok(service.findByPublisher(publisher));
    }

    @GetMapping("/book/author/{id}")
    public ResponseEntity<Iterable<BookDTO>> getBooksByAuthorId(@PathVariable UUID id) {
        log.debug("Fetch all books by Author ID");
        return ResponseEntity.ok(service.findByAuthorId(id));
    }

    @PatchMapping("/book/{id}/title")
    public ResponseEntity<BookDTO> setTitle(@PathVariable UUID id, @RequestBody String title) {
        BookDTO findBook = service.findById(id);
        findBook.setTitle(title);
        BookDTO result = service.save(findBook);

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
    public ResponseEntity<BookDTO> setIsbn(@PathVariable UUID id, @RequestBody String isbn) {
        BookDTO findBook = service.findById(id);
        findBook.setIsbn(isbn);
        BookDTO result = service.save(findBook);

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
    public ResponseEntity<BookDTO> setPublisher(@PathVariable UUID id, @RequestBody String publisher) {
        BookDTO findBook = service.findById(id);
        findBook.setPublisher(publisher);
        BookDTO result = service.save(findBook);

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

    @PatchMapping("/book/{id}/authorId")
    public ResponseEntity<BookDTO> setAuthorId(@PathVariable UUID id, @RequestBody UUID authorId) {
        BookDTO findBook = service.findById(id);
        findBook.setAuthorId(authorId);
        BookDTO result = service.save(findBook);

        log.debug("Book Author ID Updated : {}", result.toString());

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
    @RequestMapping(value="/book",
            method = {RequestMethod.POST,RequestMethod.PUT})
    public ResponseEntity<?> createBook(
            @Valid @RequestBody BookDTO book,
            Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .body(DomainValidationErrorBuilder
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

    @DeleteMapping("/book/{id}")
    public ResponseEntity<BookDTO> deleteBook(@PathVariable UUID id) {
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
    public DomainValidationError handleException(Exception exception
            /*DataAccessException exception*/) {
        return new DomainValidationError(exception.getMessage());
    }
}
