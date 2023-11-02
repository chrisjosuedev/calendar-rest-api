package dev.chrisjosue.calendarapi.common;

import java.util.List;

public interface EntityService<T, V, ID> {
    T save(V entityDto, String user);
    T update(V entityDto, ID id, String user);
    void delete(ID id, String user);
    List<T> findAll();
}
