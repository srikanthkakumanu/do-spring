package books.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import books.domain.Book;

import java.util.List;

//@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findAllByTitle(String title);
}
