package books.service;

import books.model.SortOrder;

public interface AuthorService<T> extends CommonService<T> {

    public Iterable<T> findByFirstName(String firstName);

    public Iterable<T> findByLastName(String lastName);

    public Iterable<T> findByFirstNameAndLastName(String firstName, String lastName);

    public Iterable<T> findByGenre(String genre);

    public Iterable<T> findAll(int pageNum, int pageSize);

    Iterable<T> findByFirstNameAndLastName(String firstName,
                                           String lastName,
                                           int pageNum,
                                           int pageSize,
                                           Boolean sorted,
                                           SortOrder sortOrder);

    Iterable<T> findByFirstName(String firstName,
                                int pageNum,
                                int pageSize,
                                Boolean sorted,
                                SortOrder sortOrder);

    Iterable<T> findByLastName(String lastName,
                               int pageNum,
                               int pageSize,
                               Boolean sorted,
                               SortOrder sortOrder);

    Iterable<T> findByGenre(String genre,
                            int pageNum,
                            int pageSize,
                            Boolean sorted,
                            SortOrder sortOrder);
}
