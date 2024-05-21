package books.service;

import java.util.Collection;
import java.util.UUID;

public interface CommonService<T> {
    public T save(T domain);
    public Iterable<T> save(Collection<T> domains);
    public T delete(T domain);
    public T delete(UUID id);
    public T findById(UUID id);
    public Iterable<T> findAll();
}
