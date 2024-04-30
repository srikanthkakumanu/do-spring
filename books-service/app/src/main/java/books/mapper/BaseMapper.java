package books.mapper;

import books.domain.Book;
import books.model.BookDTO;

/**
 * Transforms DTO to Domain and vise versa.
 * @param <T> DTO object
 * @param <D> Domain object
 */
public interface BaseMapper<T, D> {

    public T domainToDto(D domain);
    public D dtoToDomain(T dto);

}
