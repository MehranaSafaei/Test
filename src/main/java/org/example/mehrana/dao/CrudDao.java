package org.example.mehrana.dao;

import org.example.mehrana.entity.Personnel;
import org.example.mehrana.exception.DuplicateNationalCodeException;
import org.example.mehrana.exception.SaveRecordException;

import java.util.List;
import java.util.Optional;

public interface CrudDao<T> {

    void create(T entity) throws DuplicateNationalCodeException, SaveRecordException;

    Optional<T> findById(Long id);


    void update(T entity) throws DuplicateNationalCodeException;

    void delete(Long id);

    List<T> findAll();
}
