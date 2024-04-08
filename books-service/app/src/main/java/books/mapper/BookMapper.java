package books.mapper;

import books.domain.Book;
import books.model.BookDTO;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BookMapper implements BaseMapper {

    @Override
    public BookDTO domainToDto(Book domain) {
        BookDTO dto = new BookDTO();
        if (domain.getId() != null) dto.setId(domain.getId());
        if (domain.getTitle() != null) dto.setTitle(domain.getTitle());
        if (domain.getIsbn() != null) dto.setIsbn(domain.getIsbn());
        if (domain.getPublisher() != null) dto.setPublisher(domain.getPublisher());

        return dto;
    }

    @Override
    public Book dtoToDomain(BookDTO dto) {
        Book domain = new Book();
        if (dto.getId() != null) domain.setId(dto.getId());
        if (dto.getTitle() != null) domain.setTitle(dto.getTitle());
        if (dto.getIsbn() != null) domain.setIsbn(dto.getIsbn());
        if (dto.getPublisher() != null) domain.setPublisher(dto.getPublisher());

        return domain;
    }
}
