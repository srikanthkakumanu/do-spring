package books.service;

import java.util.Collection;

public interface CommonService<T> {
    public T save(T domain);
    public T update(T domain);
    public Iterable<T> save(Collection<T> domains);
    public void delete(T domain);
    public T findById(Long id);
    public Iterable<T> findAll();
}
