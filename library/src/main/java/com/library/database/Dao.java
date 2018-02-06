package com.library.database;

import java.util.List;

public interface Dao<T> {

    T saveOrUpdate(T object);

    List<T> saveOrUpdateAll(List<T> objects);

    T getById(int id);

    List<T> getAll();

    void deleteById(int id);

    void deleteAll();

}
