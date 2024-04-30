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

        log.debug("Saving Book: {}", dto.toString());
        Book book = mapper.dtoToDomain(dto);
        Book saved = this.repository.save(book);
        log.debug("Saved Book: {}", saved.toString());

        return mapper.domainToDto(saved);
    }

    @Override
    public BookDTO update(BookDTO dto) {

        log.debug("Updating Book: {}", dto.toString());
        Book foundBook = repository.findById(dto.getId())
                                .orElseThrow(() ->
                                    new RuntimeException(String.format("Book Id: {} not found.", dto.getId())));

        if (dto.getTitle() != null) foundBook.setTitle(dto.getTitle());
        if(dto.getIsbn() != null) foundBook.setIsbn(dto.getIsbn());
        if(dto.getPublisher() != null) foundBook.setPublisher(dto.getPublisher());
        if(dto.getAuthorId() != null) foundBook.setAuthorId(dto.getAuthorId());

        Book saved = this.repository.save(foundBook);

        log.debug("Book updated: {}", saved.toString());

        return mapper.domainToDto(saved);
    }

    @Override
    public Iterable<BookDTO> save(Collection<BookDTO> dtos) {
        return dtos.stream()
                .map(this::save)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(BookDTO dto) {

        log.debug("Deleting Book: {}", dto.toString());

        this.repository.delete(mapper.dtoToDomain(dto));
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
                        "Book Not Found. title: " + title));

        return ServiceUtils.toDTOList(found, mapper);
    }

    public Iterable<BookDTO> findByIsbn(String isbn) {
        List<Book> found = repository.findByIsbn(isbn)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Book Not Found. ISBN: " + isbn));

        return ServiceUtils.toDTOList(found, mapper);
    }

    public Iterable<BookDTO> findByPublisher(String publisher) {
        List<Book> found = repository.findByPublisher(publisher)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Book Not Found. publisher: " + publisher));

        return ServiceUtils.toDTOList(found, mapper);
    }

    public Iterable<BookDTO> findByAuthorId(UUID authorId) {
        List<Book> found = repository.findByAuthorId(authorId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Book Not Found. AuthorId: " + authorId));

        return ServiceUtils.toDTOList(found, mapper);
    }

    private Book getBook(UUID id) {

        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Book Not Found. Id: " + id));


//        if (Objects.nonNull(book))
//            return book;
//        else
//            throw new RuntimeException("Book {} not found ".formatted(id));
    }
}