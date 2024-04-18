package books.service;

import books.domain.Book;
import books.mapper.BaseMapper;
import books.model.BookDTO;
import books.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
public class BookServiceImpl implements CommonService<BookDTO> {

    private final BookRepository repository;
    private final BaseMapper mapper;

    @Autowired
    public BookServiceImpl(BookRepository repository, BaseMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    public BookDTO save(BookDTO dto) {

        log.debug("Saving Book: " + dto.toString());
        Book book = mapper.dtoToDomain(dto);
        Book saved = this.repository.save(book);
        log.debug("Saved Book: " + saved.toString());

        return mapper.domainToDto(saved);
    }

    @Override
    public BookDTO update(BookDTO dto) {

        log.debug("Updating Book: {}", dto.toString());
        Book foundBook = repository.findById(dto.getId())
                                .orElseThrow(() ->
                                    new RuntimeException("Book Id: {} not found".formatted(dto.getId())));

        if (dto.getTitle() != null) foundBook.setTitle(dto.getTitle());
        if(dto.getIsbn() != null) foundBook.setIsbn(dto.getIsbn());
        if(dto.getPublisher() != null) foundBook.setPublisher(dto.getPublisher());

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
    public BookDTO findById(Long id) {
        return mapper.domainToDto(getBook(id));
    }

    @Override
    public Iterable<BookDTO> findAll() {
        List<Book> allBooks = repository.findAll();
        return allBooks.stream()
                .map(mapper::domainToDto)
                .collect(Collectors.toList());
    }

    public Iterable<BookDTO> findAllByTitle(String title) {
        List<Book> allBooks = repository.findAllByTitle(title);
        return allBooks.stream()
                .map(mapper::domainToDto)
                .collect(Collectors.toList());
    }

    private Book getBook(Long id) {

        Book book = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Book Not Found. Id: " + id));

        if (Objects.nonNull(book))
            return book;
        else
            throw new RuntimeException("Book {} not found ".formatted(id));
    }
}