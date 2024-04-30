package books.repository;

import books.domain.Author;
import books.domain.Book;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {
    Optional<List<Author>> findByFirstName(String firstName);
    Optional<List<Author>> findByLastName(String lastName);
    Optional<List<Author>> findByGenre(String genre);
    Optional<List<Author>> findByFirstNameAndLastName(String firstName, String lastName);
    //Optional<Author> findAuthorByFirstNameAndLastName(String firstName, String lastName);

    Page<Author> findAuthorByFirstNameAndLastName(String firstName, String lastName, Pageable pageable);
    Page<Author> findAuthorByFirstName(String firstName, Pageable pageable);
    Page<Author> findAuthorByLastName(String lastName, Pageable pageable);
    Page<Author> findAuthorByGenre(String genre, Pageable pageable);

}
