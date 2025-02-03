package org.example.mehrana.dao;

import org.example.mehrana.exception.DuplicateNationalCodeException;
import org.example.mehrana.exception.SaveRecordException;

public interface CrudDao<T>{

     void create(T personnel) throws DuplicateNationalCodeException, SaveRecordException;


}
