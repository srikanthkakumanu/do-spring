package books.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import books.domain.Book;

import java.util.List;
import java.util.UUID;

//@Repository
public interface BookRepository extends JpaRepository<Book, UUID> {

    List<Book> findByTitle(String title);
    List<Book> findByIsbn(String isbn);
    List<Book> findByPublisher(String publisher);
    List<Book> findByAuthorId(UUID authorId);
}
