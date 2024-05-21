package books.controller;

import books.model.AuthorDTO;
import books.model.BookDTO;
import books.service.BookService;
import books.validation.DomainValidationError;
import books.validation.builder.DomainValidationErrorBuilder;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
@RequestMapping("/api/books")
@RestController
public class BookController {


    private final BookService<BookDTO> service;


    @Autowired
    private BookController(BookService<BookDTO> bookService) {
        this.service = bookService;
    }

    @GetMapping
    private ResponseEntity<Iterable<BookDTO>> getBooks() {
        log.debug("Fetch all books");
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{id}")
    private ResponseEntity<BookDTO> getBookById(@PathVariable UUID id) {
        log.debug("Fetch Book: [id: {}]", id);
        return ResponseEntity.ok(service.findById(id));
    }

    @GetMapping("/title")
    private ResponseEntity<Iterable<BookDTO>> getBooksByTitle(
            @RequestParam String title) {

        log.debug("Fetch all books: [title: {}]", title);
        return ResponseEntity.ok(service.findByTitle(title));
    }

    @GetMapping("/isbn")
    private ResponseEntity<Iterable<BookDTO>> getBooksByIsbn(
            @RequestParam String isbn) {

        log.debug("Fetch all books: [isbn: {}]", isbn);
        return ResponseEntity.ok(service.findByIsbn(isbn));
    }

    @GetMapping("/publisher")
    private ResponseEntity<Iterable<BookDTO>> getBooksByPublisher(
            @RequestParam String publisher) {

        log.debug("Fetch all books: [publisher: {}]", publisher);
        return ResponseEntity.ok(service.findByPublisher(publisher));
    }

    @GetMapping("/author")
    private ResponseEntity<Iterable<BookDTO>> getBooksByAuthorId(
            @RequestParam UUID authorId) {

        log.debug("Fetch all books: [authorId: {}]", authorId);
        return ResponseEntity.ok(service.findByAuthorId(authorId));
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
    @RequestMapping(method = {RequestMethod.POST,RequestMethod.PUT})
    private ResponseEntity<?> createBook(
            @Valid @RequestBody BookDTO book,
            Errors errors) {

        if (errors.hasErrors()) {
            return ResponseEntity
                    .badRequest()
                    .body(DomainValidationErrorBuilder
                            .fromBindingErrors(errors));
        }

        BookDTO result = service.save(book);

        log.debug("Book Saved : [{}]", result.toString());

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(result.getId()).toUri();
        return ResponseEntity.created(location).build();
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<BookDTO> deleteBook(@PathVariable UUID id) {

        log.debug("Delete Book : [{}]", id.toString());

        BookDTO deleted = service.delete(BookDTO.builder().id(id).build());

        log.debug("Book Deleted : [{}]", deleted);

        return ResponseEntity.ok(deleted);

    }

    @DeleteMapping
    private ResponseEntity<BookDTO> deleteBook(@RequestBody BookDTO book) {

        log.debug("Delete Book: [{}]", book);

        BookDTO deleted = service.delete(book);

        log.debug("Book Deleted : [{}]", deleted);

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

//    @PatchMapping("/{id}/title")
//    private ResponseEntity<BookDTO> setTitle(@PathVariable UUID id, @RequestBody String title) {
//        BookDTO findBook = service.findById(id);
//        findBook.setTitle(title);
//        BookDTO result = service.save(findBook);
//
//        log.debug("Book Title Updated : {}", result.toString());
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
//    @PatchMapping("/{id}/isbn")
//    private ResponseEntity<BookDTO> setIsbn(@PathVariable UUID id, @RequestBody String isbn) {
//        BookDTO findBook = service.findById(id);
//        findBook.setIsbn(isbn);
//        BookDTO result = service.save(findBook);
//
//        log.debug("Book ISBN Updated : {}", result.toString());
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
//    @PatchMapping("/{id}/publisher")
//    private ResponseEntity<BookDTO> setPublisher(@PathVariable UUID id, @RequestBody String publisher) {
//        BookDTO findBook = service.findById(id);
//        findBook.setPublisher(publisher);
//        BookDTO result = service.save(findBook);
//
//        log.debug("Book Publisher Updated : {}", result.toString());
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
//    @PatchMapping("/{id}/authorId")
//    private ResponseEntity<BookDTO> setAuthorId(@PathVariable UUID id, @RequestBody UUID authorId) {
//        BookDTO findBook = service.findById(id);
//        findBook.setAuthorId(authorId);
//        BookDTO result = service.save(findBook);
//
//        log.debug("Book Author ID Updated : {}", result.toString());
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
