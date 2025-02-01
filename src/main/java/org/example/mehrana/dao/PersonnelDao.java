package org.example.mehrana.dao;

import jakarta.persistence.*;
import org.example.mehrana.entity.Personnel;
import org.example.mehrana.exception.DuplicateNationalCodeException;

import java.util.List;
import java.util.Optional;

public class PersonnelDao implements CrudDao<Personnel> {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("PersonnelUnit");

    private final EntityManager entityManager;

    public PersonnelDao() {
        this.entityManager = entityManagerFactory.createEntityManager();
    }

    @Override
    public void create(Personnel personnel) throws DuplicateNationalCodeException {
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            if (isNationalIdDuplicated(personnel.getNationalCode())) {
                throw new DuplicateNationalCodeException();
            }

            entityTransaction.begin();
            entityManager.persist(personnel);
            entityTransaction.commit();

        } catch (Exception e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw e;
        }
    }

    public Personnel create(Personnel personnel, String nationalCode) throws DuplicateNationalCodeException {
        return null;
    }

    public Personnel findById(Long id) {
        return entityManager.find(Personnel.class, id);
    }

    public void update(Personnel personnel) throws DuplicateNationalCodeException {
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            if (isNationalIdDuplicated(personnel.getNationalCode())) {
                throw new DuplicateNationalCodeException();
            }

            entityTransaction.begin();
            entityManager.merge(personnel);
            entityTransaction.commit();
        } catch (Exception e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw e;
        }
    }

    public Personnel delete(Personnel entity) {
        return null;
    }

    public List<Personnel> findAll() {
        return List.of();
    }

    public void delete(Long id) {
        EntityTransaction entityTransaction = entityManager.getTransaction();

        try {
            Personnel personnel = entityManager.find(Personnel.class, id);
            if (personnel != null) {
                entityTransaction.begin();
                entityManager.remove(personnel);
                entityTransaction.commit();
            }
        } catch (Exception e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw e;
        }
    }

    public boolean isNationalIdDuplicated(long nationalCode) {
        Long count = entityManager.createNamedQuery("CountByNationalCode", Long.class)
                .setParameter("nationalCode", nationalCode)
                .getSingleResult();
        return count > 0;
    }


    public Optional<Personnel> findByNationalCode(long nationalCode) {
        try {
            Personnel personnel = entityManager.createNamedQuery(
                            "SelectByNationalCode", Personnel.class)
                    .setParameter("nationalCode", nationalCode)
                    .getSingleResult();
            return Optional.ofNullable(personnel);
        } catch (NoResultException e) {
            return Optional.empty();
        }
    }
}
