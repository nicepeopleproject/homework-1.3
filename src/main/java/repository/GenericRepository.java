package repository;

import java.util.List;

public interface GenericRepository<T, ID> {

    T save(T t);

    T update(T t);

    T getById(ID id);
    // Думал о том чтобы убрать получение по ID из репозитория так как в любом случае каждый раз буду читать файл
    // целиком, лучше этот функционал реализовать в Controller, но оказалось не удобным и может быть с другими репозиториями будет удобнее получать объект по ID

    List<T> getAll();

    void deleteByID(ID id);
}
