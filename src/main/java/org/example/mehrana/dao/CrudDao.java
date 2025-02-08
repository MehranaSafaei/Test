package org.example.mehrana.dao;

import org.example.mehrana.exception.DuplicateNationalCodeException;
import org.example.mehrana.exception.SaveRecordException;

import java.util.List;

public interface CrudDao<T>{

     void create(T personnel) throws DuplicateNationalCodeException, SaveRecordException;
     void delete(Long id);
      T findById(Long id);
     void update(T entity) throws DuplicateNationalCodeException;
    List<T> findAll();

}
