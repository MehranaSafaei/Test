package org.example.mehrana.dao;

import org.example.mehrana.exception.DuplicateNationalCodeException;

public interface CrudDao<T>{

    public void create(T personnel) throws DuplicateNationalCodeException;


}
