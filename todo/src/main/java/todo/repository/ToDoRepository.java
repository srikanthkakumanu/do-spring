package todo.repository;

import org.springframework.stereotype.Repository;
import todo.domain.ToDo;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Repository
public class ToDoRepository implements CommonRepository<ToDo> {

    private Map<String, ToDo> toDos = new HashMap<>();

    private Comparator<Map.Entry<String, ToDo>> entryComparator =
            (Map.Entry<String, ToDo> o1,
             Map.Entry<String, ToDo> o2) -> {
                return o1.getValue().getCreated().compareTo(o2.getValue().getCreated());
            };

    @Override
    public ToDo save(final ToDo domain) {
        ToDo updated = domain;
        ToDo result = toDos.get(updated.getId());

        if (Objects.nonNull(result)) {
            result.setModified(LocalDateTime.now());
            result.setDescription(updated.getDescription());
            result.setCompleted(updated.isCompleted());
            updated = result;
        }
        toDos.put(updated.getId(), updated);

        return toDos.get(updated.getId());
    }

    @Override
    public Iterable<ToDo> save(Collection<ToDo> domains) {
        domains.forEach(this::save);
        return findAll();
    }

    @Override
    public void delete(ToDo domain) {
        toDos.remove(domain.getId());
    }

    @Override
    public ToDo findById(String id) {
        return toDos.get(id);
    }

    @Override
    public Iterable<ToDo> findAll() {
        return toDos.entrySet()
                .stream()
                .sorted(entryComparator)
                .map(Map.Entry::getValue)
                .collect(Collectors.toList());
    }
}
