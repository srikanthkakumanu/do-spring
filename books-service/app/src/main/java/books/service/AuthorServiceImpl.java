package books.service;

import books.domain.Author;
import books.mapper.BaseMapper;
import books.model.AuthorDTO;
import books.repository.AuthorRepository;
import books.util.ServiceUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;
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

    @Override
    public AuthorDTO save(AuthorDTO dto) {
        log.debug("Saving Author: {}", dto.toString());
        Author author = mapper.dtoToDomain(dto);
        Author saved = this.repository.save(author);
        log.debug("Saved Author: {}", saved.toString());

        return mapper.domainToDto(saved);
    }

    @Override
    public AuthorDTO update(AuthorDTO dto) {
        log.debug("Updating Author: {}", dto.toString());
        Author found = repository.findById(dto.getId())
                .orElseThrow(() ->
                        new RuntimeException(String.format("Author Id: {} not found.", dto.getId())));

        if (dto.getFirstName() != null) found.setFirstName(dto.getFirstName());
        if(dto.getLastName() != null) found.setLastName(dto.getLastName());
        if(dto.getGenre() != null) found.setGenre(dto.getGenre());

        Author saved = this.repository.save(found);

        log.debug("Author updated: {}", saved.toString());

        return mapper.domainToDto(saved);
    }

    @Override
    public Iterable<AuthorDTO> save(Collection<AuthorDTO> dtos) {
        log.debug("Saving Authors");

        return dtos.stream()
                .map(this::save)
                .collect(Collectors.toList());
    }

    @Override
    public void delete(AuthorDTO dto) {
        log.debug("Deleting Author: {}", dto.toString());

        this.repository.delete(mapper.dtoToDomain(dto));
    }

    @Override
    public AuthorDTO findById(UUID id) {
        return mapper.domainToDto(getAuthor(id));
    }

    @Override
    public Iterable<AuthorDTO> findAll() {
        List<Author> allAuthors = repository.findAll();
        return ServiceUtils.toDTOList(allAuthors, mapper);
    }

    @Override
    public Iterable<AuthorDTO> findByFirstNameAndLastName(String firstName, String lastName) {
        List<Author> found =
                repository.findByFirstNameAndLastName(firstName, lastName)
                        .orElseThrow(() ->
                                new ResponseStatusException(HttpStatus.NOT_FOUND,
                                        "Author Not Found. firstName: {}, lastName: {}"
                                                .formatted(firstName, lastName)));

        return ServiceUtils.toDTOList(found, mapper);
    }

    @Override
    public Iterable<AuthorDTO> findByFirstName(String firstName) {

        List<Author> found = repository.findByFirstName(firstName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Author Not Found. firstName: " + firstName));

        return ServiceUtils.toDTOList(found, mapper);
    }

    @Override
    public Iterable<AuthorDTO> findByLastName(String lastName) {
        List<Author> found = repository.findByLastName(lastName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Author Not Found. lastName: " + lastName));

        return ServiceUtils.toDTOList(found, mapper);
    }

    @Override
    public Iterable<AuthorDTO> findByGenre(String genre) {
        List<Author> found = repository.findByGenre(genre)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Author Not Found. genre: " + genre));

        return ServiceUtils.toDTOList(found, mapper);
    }

    @Override
    public Iterable<AuthorDTO> findAuthorsByFirstNameAndLastName(String firstName,
                                                                String lastName,
                                                                int pageNum,
                                                                int pageSize) {
        List<Author> found =
                repository.findAuthorByFirstNameAndLastName(firstName,
                                lastName,
                                PageRequest.of(pageNum, pageSize)).toList();

        if (found.isEmpty())
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Author Not Found. firstName: {}, lastName: {}"
                            .format(firstName, lastName));

        return ServiceUtils.toDTOList(found, mapper);
    }

    @Override
    public Iterable<AuthorDTO> findAuthorsByFirstName(String firstName,
                                                     int pageNum,
                                                     int pageSize) {
        List<Author> found =
                repository.findAuthorByFirstName(firstName,
                        PageRequest.of(pageNum, pageSize)).toList();

        if (found.isEmpty())
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Author Not Found. firstName: {}" + firstName);

        return ServiceUtils.toDTOList(found, mapper);
    }

    @Override
    public Iterable<AuthorDTO> findAuthorsByLastName(String lastName,
                                                    int pageNum,
                                                    int pageSize) {
        List<Author> found =
                repository.findAuthorByLastName(lastName,
                        PageRequest.of(pageNum, pageSize)).toList();

        if (found.isEmpty())
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Author Not Found. lastName: {}" + lastName);

        return ServiceUtils.toDTOList(found, mapper);
    }

    @Override
    public Iterable<AuthorDTO> findAuthorsByGenre(String genre,
                                                 int pageNum,
                                                 int pageSize) {
        List<Author> found =
                repository.findAuthorByGenre(genre,
                        PageRequest.of(pageNum, pageSize)).toList();

        if (found.isEmpty())
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Author Not Found. Genre: {}" + genre);

        return ServiceUtils.toDTOList(found, mapper);

    }

    private Author getAuthor(UUID id) {

        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Author Not Found. Id: " + id));
    }
}
