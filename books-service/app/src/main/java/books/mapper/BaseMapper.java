package books.mapper;

import books.domain.Book;
import books.model.BookDTO;

public interface BaseMapper {

    public BookDTO domainToDto(Book domain);
    public Book dtoToDomain(BookDTO dto);

}
