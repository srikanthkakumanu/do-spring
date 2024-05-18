package books.mapper;

import books.domain.Book;
import books.model.BookDTO;

/**
 * Transforms DTO to Domain and vise versa.
 * @param <T> DTO object
 * @param <D> Domain object
 */
sealed public interface BaseMapper<T, D> permits AuthorMapper, BookMapper {

    public T domainToDto(D domain);
    public D dtoToDomain(T dto, D domain);
}
