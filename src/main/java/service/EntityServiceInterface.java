package service;

import org.springframework.stereotype.Service;

import java.util.List;


public interface EntityServiceInterface<T> {

    T findById(int id);
    List<T> findAll();
    T create(T entity);
    void delete(T entity);
    T update(T entity);
}
