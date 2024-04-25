package books.repository;

import books.domain.Author;
import books.domain.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {
    List<Book> findByFirstName(String firstName);
    List<Book> findByLastName(String lastName);
    List<Book> findByGenre(String genre);

}
