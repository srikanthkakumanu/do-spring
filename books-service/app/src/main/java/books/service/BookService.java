package books.service;

import java.util.UUID;

public interface BookService<T> extends CommonService<T> {
    public Iterable<T> findByTitle(String title);
    public Iterable<T> findByIsbn(String isbn);
    public Iterable<T> findByPublisher(String publisher);
    public Iterable<T> findByAuthorId(UUID id);
}
