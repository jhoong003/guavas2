package com.example.guavas.firebaseDAO;

import java.util.List;
import java.util.Optional;

/**
 * The interface for the other DAOs. Responsible for saving data to the database.
 * @param <T> the Type.
 */
public interface MedsDAO<T>{
    Optional<T> get(long id);

    List<T> getAll();

    void save(T t);

    void update(T t, String[] params);

    void delete(T t);
}