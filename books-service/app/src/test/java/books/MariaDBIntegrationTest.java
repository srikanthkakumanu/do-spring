package books;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@ActiveProfiles("dev")
@DataJpaTest
@ComponentScan(basePackages = {"books"})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class MariaDBIntegrationTest {

//    @Autowired
//    BookRepository bookRepository;
//
//    @Autowired
//    AuthorUUIDRepository authorUUIDRepository;
//
//    @Autowired
//    BookUUIDRepository bookUUIDRepository;
//
//    @Autowired
//    AuthorCompositeEmbeddedRepository authorCompositeEmbeddedRepository;
//
//    @Test
//    void authorCompositeEmbeddedTest() {
//        NameId nameId = new NameId("Funny", "Bones");
//        AuthorCompositeEmbedded authorCompositeEmbedded = new AuthorCompositeEmbedded(nameId);
//
//        AuthorCompositeEmbedded saved = authorCompositeEmbeddedRepository.save(authorCompositeEmbedded);
//        assertThat(saved).isNotNull();
//
//        AuthorCompositeEmbedded fetched = authorCompositeEmbeddedRepository.getById(nameId);
//        assertThat(fetched).isNotNull();
//    }
//
//
//    @Test
//    void testBookUUID() {
//        BookUUID bookUuid = bookUUIDRepository.save(new BookUUID());
//        assertThat(bookUuid).isNotNull();
//        assertThat(bookUuid.getId());
//
//        BookUUID fetched = bookUUIDRepository.getById(bookUuid.getId());
//        assertThat(fetched).isNotNull();
//    }
//
//    @Test
//    void testAuthorUUID() {
//        AuthorUUID authorUuid = authorUUIDRepository.save(new AuthorUUID());
//        assertThat(authorUuid).isNotNull();
//        assertThat(authorUuid.getId()).isNotNull();
//
//        AuthorUUID fetched = authorUUIDRepository.getById(authorUuid.getId());
//        assertThat(fetched).isNotNull();
//    }
//
//    @Test
//    void testMariaDB() {
//        long countBefore = bookRepository.count();
//        assertThat(countBefore).isEqualTo(3);
//    }
}