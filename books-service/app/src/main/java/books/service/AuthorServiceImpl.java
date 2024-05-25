package books.service;

import books.domain.Author;
import books.mapper.BaseMapper;
import books.model.AuthorDTO;
import books.model.SortOrder;
import books.repository.AuthorRepository;
import books.util.ServiceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AuthorServiceImpl implements AuthorService<AuthorDTO> {

    private final AuthorRepository repository;
    private final BaseMapper<AuthorDTO, Author> mapper;

    public AuthorServiceImpl(AuthorRepository repository,
                             BaseMapper<AuthorDTO, Author> mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public AuthorDTO save(AuthorDTO dto) {
        log.debug("Save Author: [{}]", dto.toString());

        Author found = (Objects.nonNull(dto.getId()))
                ? repository.findById(dto.getId())
                .orElse(new Author())
                : new Author();

        Author merged = mapper.dtoToDomain(dto, found);
        Author saved = this.repository.save(merged);
        log.debug("Saved Author: [{}]", saved);
        return mapper.domainToDto(saved);
    }

    @Override
    public Iterable<AuthorDTO> save(Collection<AuthorDTO> dtos) {
        log.debug("Save Authors");

        return dtos.stream()
                .map(this::save)
                .collect(Collectors.toList());
    }

    @Override
    public AuthorDTO delete(AuthorDTO dto) {

        log.debug("Delete Author: [{}]", dto.toString());

        Author deleteAuthor = getAuthor(dto.getId());

        this.repository.delete(deleteAuthor);

        log.debug("Author Deleted: [{}]", deleteAuthor);

        return mapper.domainToDto(deleteAuthor);
    }

    @Override
    public AuthorDTO delete(UUID id) {

        log.debug("Delete Author: [{}]", id);

        Author deleteAuthor = getAuthor(id);

        this.repository.delete(deleteAuthor);

        log.debug("Author Deleted: [{}]", deleteAuthor);

        return mapper.domainToDto(deleteAuthor);
    }

    @Override
    public Iterable<AuthorDTO> findAll() {
        log.debug("Get all authors");
        List<Author> allAuthors = repository.findAll();
        return ServiceUtils.toDTOList(allAuthors, mapper);
    }

    @Override
    public AuthorDTO findById(UUID id) {
        return mapper.domainToDto(getAuthor(id));
    }

    @Override
    public Iterable<AuthorDTO> findByFirstNameAndLastName(String firstName, String lastName) {
        log.debug("Get all authors: [firstName: {} and lastName: {}]", firstName, lastName);

        List<Author> found =
                repository.findByFirstNameAndLastName(firstName, lastName)
                        .orElseThrow(() ->
                                new ResponseStatusException(HttpStatus.NOT_FOUND,
                                        "Author[firstName: %s, lastName: %s] Not Found. "
                                                .formatted(firstName, lastName)));

        return ServiceUtils.toDTOList(found, mapper);
    }

    @Override
    public Iterable<AuthorDTO> findByFirstName(String firstName) {
        log.debug("Get all authors: [firstName: {}]", firstName);

        List<Author> found = repository.findByFirstName(firstName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Author[firstName: %s] Not Found.".formatted(firstName)));

        return ServiceUtils.toDTOList(found, mapper);
    }

    @Override
    public Iterable<AuthorDTO> findByLastName(String lastName) {

        log.debug("Get all authors: [lastName: {}]", lastName);

        List<Author> found = repository.findByLastName(lastName)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Author[lastName: %s] Not Found.".formatted(lastName)));

        return ServiceUtils.toDTOList(found, mapper);
    }

    @Override
    public Iterable<AuthorDTO> findByGenre(String genre) {
        log.debug("Get all authors: [genre: {}]", genre);

        List<Author> found = repository.findByGenre(genre)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Author[genre: %s] Not Found.".formatted(genre)));

        return ServiceUtils.toDTOList(found, mapper);
    }

    @Override
    public Iterable<AuthorDTO> findAll(int pageNum, int pageSize) {

        log.debug("Get all authors: [pageNum: {}, pageSize: {}]", pageNum, pageSize);

        List<Author> allAuthors = repository.findAll(PageRequest.of(pageNum, pageSize)).getContent();

        return ServiceUtils.toDTOList(allAuthors, mapper);
    }

    @Override
    public Iterable<AuthorDTO> findByFirstNameAndLastName(String firstName,
                                                          String lastName,
                                                          int pageNum,
                                                          int pageSize,
                                                          Boolean sorted,
                                                          SortOrder sortOrder) {

        log.debug("Get all authors: [" +
                        "firstName: {}, lastName: {}, " +
                        "pageNum: {}, pageSize: {}, sorted: {}, sortOrder: {}]",
                firstName, lastName, pageNum, pageSize, sorted, sortOrder);

        List<Author> found;
        if (!sorted)
            found = repository.findByFirstNameAndLastName(
                    firstName,
                    lastName,
                    PageRequest.of(pageNum, pageSize))
                    .getContent();
        else {
            if (sortOrder == SortOrder.ASC)
                found = repository.findByFirstNameAndLastName(
                        firstName,
                        lastName,
                        PageRequest.of(
                                pageNum,
                                pageSize,
                                Sort.by(Sort.Order.asc("firstName"),
                                        Sort.Order.asc("lastName"))))
                        .getContent();
            else
                found = repository.findByFirstNameAndLastName(
                        firstName,
                        lastName,
                        PageRequest.of(
                                pageNum,
                                pageSize,
                                Sort.by(Sort.Order.desc("firstName"),
                                        Sort.Order.desc("lastName"))))
                        .getContent();
        }

        if (found.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Authors: [firstName: %s, lastName: %s] Not Found.".formatted(firstName, lastName));

        return ServiceUtils.toDTOList(found, mapper);
    }

    @Override
    public Iterable<AuthorDTO> findByFirstName(String firstName,
                                               int pageNum,
                                               int pageSize,
                                               Boolean sorted,
                                               SortOrder sortOrder) {

        log.debug("Get all authors: [" +
                        "firstName: {}, " +
                        "pageNum: {}, pageSize: {}, sorted: {}, sortOrder: {}]",
                firstName, pageNum, pageSize, sorted, sortOrder);

        List<Author> found;
        if (!sorted)
            found = repository.findByFirstName(firstName, PageRequest.of(pageNum, pageSize)).getContent();
        else {
            if (sortOrder == SortOrder.ASC)
                found = repository.findByFirstName(firstName, PageRequest.of(pageNum, pageSize, Sort.by(Sort.Order.asc("firstName")))).getContent();
            else
                found = repository.findByFirstName(firstName, PageRequest.of(pageNum, pageSize, Sort.by(Sort.Order.desc("firstName")))).getContent();
        }

        if (found.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Authors: [firstName: %s] Not Found.".formatted(firstName));

        return ServiceUtils.toDTOList(found, mapper);
    }

    @Override
    public Iterable<AuthorDTO> findByLastName(String lastName,
                                              int pageNum,
                                              int pageSize,
                                              Boolean sorted,
                                              SortOrder sortOrder) {

        log.debug("Get all authors: [" +
                        "lastName: {}, " +
                        "pageNum: {}, pageSize: {}, sorted: {}, sortOrder: {}]",
                lastName, pageNum, pageSize, sorted, sortOrder);

        List<Author> found;
        if (!sorted)
            found = repository.findByLastName(lastName, PageRequest.of(pageNum, pageSize)).getContent();
        else {
            if (sortOrder == SortOrder.ASC)
                found = repository.findByLastName(lastName, PageRequest.of(pageNum, pageSize, Sort.by(Sort.Order.asc("lastName")))).getContent();
            else
                found = repository.findByLastName(lastName, PageRequest.of(pageNum, pageSize, Sort.by(Sort.Order.desc("lastName")))).getContent();
        }

        if (found.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Authors: [lastName: %s] Not Found.".formatted(lastName));

        return ServiceUtils.toDTOList(found, mapper);
    }

    @Override
    public Iterable<AuthorDTO> findByGenre(String genre,
                                           int pageNum,
                                           int pageSize,
                                           Boolean sorted,
                                           SortOrder sortOrder) {

        log.debug("Get all authors: [" +
                        "genre: {}, " +
                        "pageNum: {}, pageSize: {}, sorted: {}, sortOrder: {}]",
                genre, pageNum, pageSize, sorted, sortOrder);

        List<Author> found;
        if (!sorted)
            found = repository.findByGenre(genre, PageRequest.of(pageNum, pageSize)).getContent();
        else {
            if (sortOrder == SortOrder.ASC)
                found = repository.findByGenre(genre, PageRequest.of(pageNum, pageSize, Sort.by(Sort.Order.asc("genre")))).getContent();
            else
                found = repository.findByGenre(genre, PageRequest.of(pageNum, pageSize, Sort.by(Sort.Order.desc("genre")))).getContent();
        }

        if (found.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    "Authors: [genre: %s] Not Found.".formatted(genre));

        return ServiceUtils.toDTOList(found, mapper);
    }

    private Author getAuthor(UUID id) {
        log.debug("getAuthor[Id: %s]".formatted(id));

        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Author[Id: %s] Not Found.".formatted(id)));
    }
}
