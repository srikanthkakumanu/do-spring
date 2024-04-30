package books.util;

import books.mapper.BaseMapper;

import java.util.List;
import java.util.stream.Collectors;

public class ServiceUtils<T, D> {

    public static <T, D> List<T> toDTOList(List<D> matched, BaseMapper<T, D> mapper) {
        return matched.stream()
                .map(mapper::domainToDto)
                .collect(Collectors.toList());
    }
}
