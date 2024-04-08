package books;

import books.repositories.BookRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("dev")
@DataJpaTest
@ComponentScan("books.bootstrap")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class BookIntegrationTest {
//
//    @Autowired
//    BookRepository bookRepository;
//
//    @Test
//    void testDB() {
//        long countBefore = bookRepository.count();
//        assertThat(countBefore).isEqualTo(2);
//    }
}
