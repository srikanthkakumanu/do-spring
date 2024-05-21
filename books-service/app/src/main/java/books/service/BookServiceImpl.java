package books.service;

import books.domain.Book;
import books.mapper.BaseMapper;
import books.model.BookDTO;
import books.repository.BookRepository;
import books.util.ServiceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BookServiceImpl implements BookService<BookDTO> {

    private final BookRepository repository;
    private final BaseMapper<BookDTO, Book> mapper;

    @Autowired
    public BookServiceImpl(BookRepository repository, BaseMapper<BookDTO, Book> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public BookDTO save(BookDTO dto) {
        log.debug("Save Book: {}", dto.toString());

        Book found = (Objects.nonNull(dto.getId()))
                            ? repository.findById(dto.getId())
                                        .orElse(new Book())
                            : new Book();

        Book merged = mapper.dtoToDomain(dto, found);
        Book saved = this.repository.save(merged);
        log.debug("Saved Book: {}", saved.toString());
        return mapper.domainToDto(saved);
    }

    @Override
    public Iterable<BookDTO> save(Collection<BookDTO> dtos) {
        return dtos.stream()
                .map(this::save)
                .collect(Collectors.toList());
    }

    @Override
    public BookDTO delete(BookDTO dto) {

        log.debug("Delete Book: [{}]", dto.toString());

        Book deleteBook = getBook(dto.getId());

        this.repository.delete(deleteBook);

        log.debug("Book Deleted: [{}]", deleteBook);

        return mapper.domainToDto(deleteBook);
    }

    @Override
    public BookDTO delete(UUID id) {

        log.debug("Delete Book: [{}]", id);

        Book deleteBook = getBook(id);

        this.repository.delete(deleteBook);

        log.debug("Book Deleted: [{}]", deleteBook);

        return mapper.domainToDto(deleteBook);
    }

    @Override
    public BookDTO findById(UUID id) {
        return mapper.domainToDto(getBook(id));
    }

    @Override
    public Iterable<BookDTO> findAll() {
        List<Book> allBooks = repository.findAll();

        return ServiceUtils.toDTOList(allBooks, mapper);
    }

    public Iterable<BookDTO> findByTitle(String title) {

        List<Book> found = repository.findByTitle(title)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Books: [title: %s] Not Found.".formatted(title)));

        return ServiceUtils.toDTOList(found, mapper);
    }

    public Iterable<BookDTO> findByIsbn(String isbn) {

        List<Book> found = repository.findByIsbn(isbn)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Books: [isbn: %s] Not Found.".formatted(isbn)));

        return ServiceUtils.toDTOList(found, mapper);
    }

    public Iterable<BookDTO> findByPublisher(String publisher) {
        List<Book> found = repository.findByPublisher(publisher)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Books: [publisher: %s] Not Found.".formatted(publisher)));

        return ServiceUtils.toDTOList(found, mapper);
    }

    public Iterable<BookDTO> findByAuthorId(UUID authorId) {

        List<Book> found = repository.findByAuthorId(authorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Books: [authorId: %s] Not Found.".formatted(authorId)));

        return ServiceUtils.toDTOList(found, mapper);
    }

    private Book getBook(UUID id) {

        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Book[Id: %s] Not Found.".formatted(id)));

    }
}