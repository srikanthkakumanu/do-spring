package books.mapper;

import books.domain.Author;
import books.domain.Book;
import books.model.AuthorDTO;
import books.model.BookDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class AuthorMapper {

    public AuthorDTO domainToDto(Author domain) {
        AuthorDTO dto = new AuthorDTO();
        if (domain.getId() != null) dto.setId(domain.getId());
        if (domain.getFirstName() != null) dto.setFirstName(domain.getFirstName());
        if (domain.getLastName() != null) dto.setLastName(domain.getLastName());
        return dto;
    }

    public Author dtoToDomain(AuthorDTO dto) {
        Author domain = new Author();
        if (dto.getId() != null) domain.setId(dto.getId());
        if (dto.getFirstName() != null) domain.setFirstName(dto.getFirstName());
        if (dto.getLastName() != null) domain.setLastName(dto.getLastName());

        return domain;
    }
}
