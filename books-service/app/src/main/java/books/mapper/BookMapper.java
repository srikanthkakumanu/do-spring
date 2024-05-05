package books.mapper;

import books.domain.Book;
import books.model.BookDTO;
import org.springframework.stereotype.Component;

@Component
public class BookMapper implements BaseMapper<BookDTO, Book> {

    @Override
    public BookDTO domainToDto(Book domain) {
        BookDTO dto = new BookDTO();
        if (domain.getId() != null) dto.setId(domain.getId());
        if (domain.getTitle() != null) dto.setTitle(domain.getTitle());
        if (domain.getIsbn() != null) dto.setIsbn(domain.getIsbn());
        if (domain.getPublisher() != null) dto.setPublisher(domain.getPublisher());
        if (domain.getCreated() != null) dto.setCreated(domain.getCreated());
        if (domain.getAuthorId() != null) dto.setAuthorId(domain.getAuthorId());
        return dto;
    }

    @Override
    public Book dtoToDomain(BookDTO dto, Book domain) {
        if (dto.getId() != null) domain.setId(dto.getId());
        if (dto.getTitle() != null) domain.setTitle(dto.getTitle());
        if (dto.getIsbn() != null) domain.setIsbn(dto.getIsbn());
        if (dto.getPublisher() != null) domain.setPublisher(dto.getPublisher());
        if (dto.getAuthorId() != null) domain.setAuthorId(dto.getAuthorId());
        return domain;
    }

}
