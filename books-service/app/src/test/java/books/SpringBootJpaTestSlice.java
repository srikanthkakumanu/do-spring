package books;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.annotation.Commit;
import books.domain.Book;
import books.repositories.BookRepository;

import static org.assertj.core.api.Assertions.assertThat;


@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DataJpaTest
@ComponentScan(basePackages = "books.bootstrap")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class SpringBootJpaTestSlice {
//
//    @Autowired
//    BookRepository bookRepository;
//
//    @Commit
//    @Order(1)
//    @Test
//    void testJpaTestSlice() {
//        var countBefore = bookRepository.count();
//        assertThat(countBefore).isEqualTo(2);
//
//        bookRepository.save(new Book("My Book", "1242343", "RandomHouse"));
//
//        var countAfter = bookRepository.count();
//
//        assertThat(countBefore).isLessThan(countAfter);
//    }
//
//    @Order(2)
//    @Test
//    void testJpaTestSliceTransaction() {
//        var countBefore = bookRepository.count();
//        assertThat(countBefore).isEqualTo(3);
//
//    }
//
}
