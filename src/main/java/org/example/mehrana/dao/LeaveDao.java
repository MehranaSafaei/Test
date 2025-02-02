package org.example.mehrana.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.mehrana.entity.Leave;
import org.example.mehrana.entity.Personnel;
import org.example.mehrana.entity.dto.LeaveDto;
import org.example.mehrana.exception.DuplicateNationalCodeException;
import org.example.mehrana.exception.SaveRecordException;
import org.example.mehrana.mapper.DtoMapper;

import java.util.List;
import java.util.Optional;

public class LeaveDao implements CrudDao<Leave> {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PersonnelUnit");
    private final EntityManager entityManager;

    public LeaveDao() {
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    public void create(Leave entity) throws SaveRecordException {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
                transaction.begin();
                entityManager.persist(entity);
                transaction.commit();

        } catch (Exception e) {
            throw new SaveRecordException();
        }
    }

    @Override
    public Optional<Leave> findById(Long id) {
        return Optional.empty();
    }

    @Override
    public void update(Leave entity) throws DuplicateNationalCodeException {

    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public List<Leave> findAll() {
        return List.of();
    }


}
