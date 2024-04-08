package books.bootstrap;

import books.repositories.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import books.domain.Book;

@Slf4j
@Profile({"default", "dev"})
@Component
public class BooksDataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;

    public BooksDataInitializer(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        bookRepository.deleteAll();

        Book bookDDD = new Book("Domain Driven Design", "123", "RandomHouse");

        System.out.printf("Id: %d\n", bookDDD.getId());

        Book savedDDD = bookRepository.save(bookDDD);

        System.out.printf("Id: %d\n", savedDDD.getId());

        Book bookSIA = new Book("Spring In Action", "234234", "O'Reilly");
        bookRepository.save(bookSIA);

        Book bookSA = new Book("Software Architecture", "234", "Self");
        bookRepository.save(bookSA);

        bookRepository.findAll().forEach(book ->
            log.debug("[Book Id: {}, Book Title: {}]", book.getId(), book.getTitle())
        );
    }
}
