package books.service;

import books.domain.Author;
import books.model.AuthorDTO;
import org.springframework.data.domain.Page;

public interface AuthorService<T> extends CommonService<T> {

    public Iterable<T> findByFirstName(String firstName);
    public Iterable<T> findByLastName(String lastName);
    public Iterable<T> findByFirstNameAndLastName(String firstName, String lastName);
    public Iterable<T> findByGenre(String genre);

    Iterable<T> findByFirstNameAndLastName(String firstName,
                                                  String lastName,
                                                  int pageNum,
                                                  int pageSize);

    Iterable<T> findByFirstName(String firstName,
                                       int pageNum,
                                       int pageSize);

    Iterable<T> findByLastName(String lastName,
                                      int pageNum,
                                      int pageSize);

    Iterable<T> findByGenre(String genre,
                                   int pageNum,
                                   int pageSize);
}
