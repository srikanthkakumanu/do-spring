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
        log.debug("Update Author: {}", dto.toString());
        Author found = repository.findById(dto.getId())
                .orElseThrow(() ->
                        new RuntimeException("Author Id: %s not found.".formatted(dto.getId())));

        if (dto.getFirstName() != null) found.setFirstName(dto.getFirstName());
        if(dto.getLastName() != null) found.setLastName(dto.getLastName());
        if(dto.getGenre() != null) found.setGenre(dto.getGenre());

        Author saved = this.repository.save(found);

        log.debug("Author updated: {}", saved.toString());

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
    public void delete(AuthorDTO dto) {
        log.debug("Delete Author: {}", dto.toString());

        this.repository.delete(mapper.dtoToDomain(dto));
    }

    @Override
    public Iterable<AuthorDTO> findAll() {
        log.debug("Get all authors ");
        List<Author> allAuthors = repository.findAll();
        return ServiceUtils.toDTOList(allAuthors, mapper);
    }

    @Override
    public AuthorDTO findById(UUID id) {
        return mapper.domainToDto(getAuthor(id));
    }

    @Override
    public Iterable<AuthorDTO> findByFirstNameAndLastName(String firstName, String lastName) {
        log.debug("Get all authors by firstName: {} and lastName: {}", firstName, lastName);

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
        log.debug("Get all authors by firstName: {}", firstName);

        List<Author> found = repository.findByFirstName(firstName)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Author[firstName: %s] Not Found.".formatted(firstName)));

        return ServiceUtils.toDTOList(found, mapper);
    }

    @Override
    public Iterable<AuthorDTO> findByLastName(String lastName) {
        log.debug("Get all authors by lastName: {}", lastName);
        List<Author> found = repository.findByLastName(lastName)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Author[lastName: %s] Not Found.".formatted(lastName)));

        return ServiceUtils.toDTOList(found, mapper);
    }

    @Override
    public Iterable<AuthorDTO> findByGenre(String genre) {
        log.debug("Get all authors by genre: {}", genre);

        List<Author> found = repository.findByGenre(genre)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Author[genre: %s] Not Found.".formatted(genre)));

        return ServiceUtils.toDTOList(found, mapper);
    }

    @Override
    public Iterable<AuthorDTO> findByFirstNameAndLastName(String firstName,
                                                                String lastName,
                                                                int pageNum,
                                                                int pageSize) {

        log.debug("Get all authors by " +
                "firstName: {}, lastName: {}" +
                "pageNum: {}, pageSize: {}",
                firstName, lastName, pageNum, pageSize);

        List<Author> found =
                repository.findByFirstNameAndLastName(firstName,
                                lastName,
                                PageRequest.of(pageNum, pageSize)).toList();

//        if (found.isEmpty())
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND,
//                    "Author[firstName: %s, lastName: %s] Not Found."
//                            .formatted(firstName, lastName));

        return ServiceUtils.toDTOList(found, mapper);
    }

    @Override
    public Iterable<AuthorDTO> findByFirstName(String firstName,
                                                     int pageNum,
                                                     int pageSize) {

        log.debug("Get all authors by " +
                        "firstName: {}" +
                        "pageNum: {}, pageSize: {}",
                firstName, pageNum, pageSize);

        List<Author> found =
                repository.findByFirstName(firstName,
                        PageRequest.of(pageNum, pageSize)).toList();

//        if (found.isEmpty())
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND,
//                    "Author[firstName: %s] Not Found.".formatted(firstName));

        return ServiceUtils.toDTOList(found, mapper);
    }

    @Override
    public Iterable<AuthorDTO> findByLastName(String lastName,
                                                    int pageNum,
                                                    int pageSize) {

        log.debug("Get all authors by " +
                        "lastName: {}" +
                        "pageNum: {}, pageSize: {}",
                lastName, pageNum, pageSize);

        List<Author> found =
                repository.findByLastName(lastName,
                        PageRequest.of(pageNum, pageSize)).toList();

//        if (found.isEmpty())
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND,
//                    "Author[lastName: %s] Not Found.".formatted(lastName));

        return ServiceUtils.toDTOList(found, mapper);
    }

    @Override
    public Iterable<AuthorDTO> findByGenre(String genre,
                                                 int pageNum,
                                                 int pageSize) {

        log.debug("Get all authors by " +
                        "genre: {}" +
                        "pageNum: {}, pageSize: {}",
                genre, pageNum, pageSize);

        List<Author> found =
                repository.findByGenre(genre,
                        PageRequest.of(pageNum, pageSize)).toList();

//        if (found.isEmpty())
//            throw new ResponseStatusException(
//                    HttpStatus.NOT_FOUND,
//                    "Author[genre: %s] Not Found.".formatted(genre));

        return ServiceUtils.toDTOList(found, mapper);
    }

    private Author getAuthor(UUID id) {
        log.debug("Author[Id: %s] Not Found.".formatted(id));

        return repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Author[Id: %s] Not Found.".formatted(id)));
    }
}
