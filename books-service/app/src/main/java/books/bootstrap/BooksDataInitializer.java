package books.bootstrap;

import books.domain.*;
import books.repository.AuthorRepository;
import books.repository.BookRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Slf4j
@Profile({"default", "dev"})
@Component
public class BooksDataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BooksDataInitializer(BookRepository bookRepository,
                                AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        createBooks();
        createAuthors();
    }

    private void createAuthors() {

        Author aOne = new Author("Craig", "Walls");
        authorRepository.save(aOne);

        Author aTwo = new Author("Richard", "Burton");
        authorRepository.save(aTwo);

        Author aThree = new Author("Mark", "Fowler");
        authorRepository.save(aThree);

        authorRepository.findAll().forEach(author ->
                log.debug("[Id: {}, First Name: {}, Last Name: {}]",
                        author.getId(), author.getFirstName(),
                        author.getLastName())
        );
    }

    private void createBooks() {
        bookRepository.deleteAll();

        Book bookDDD = new Book("Domain Driven Design", "123", "RandomHouse", UUID.randomUUID());

        System.out.printf("Id: %s\n", bookDDD.getId());

        Book savedDDD = bookRepository.save(bookDDD);

        System.out.printf("Id: %s\n", savedDDD.getId());

        Book bookSIA = new Book("Spring In Action", "234234", "O'Reilly", UUID.randomUUID());
        bookRepository.save(bookSIA);

        Book bookSA = new Book("Software Architecture", "234", "Self", UUID.randomUUID());
        bookRepository.save(bookSA);

        bookRepository.findAll().forEach(book ->
                log.debug("[Id: {}, Title: {}, ISBN: {}, Publisher: {}, AuthorID: {}]",
                        book.getId(), book.getTitle(),
                        book.getIsbn(), book.getPublisher(), book.getAuthorId())
        );
    }
}
