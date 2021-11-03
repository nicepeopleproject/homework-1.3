package controller;

import java.util.List;

public interface GenericController<T, ID> {

    void update(T t);

    void changeNameById(ID id, String newName);

    T getByID(ID id);

    List<T> getAll();

    void deleteByID(ID id);

    void deleteAll();

}
