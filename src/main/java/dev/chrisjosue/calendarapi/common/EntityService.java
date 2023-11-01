package dev.chrisjosue.calendarapi.common;

import java.util.List;

public interface EntityService<T, V, ID> {
    T save(V entityDto);
    T update(V entityDto, ID id);
    void delete(ID id);
    T findById(ID id);
    List<T> findAll();
}
