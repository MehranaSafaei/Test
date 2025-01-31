package org.example.mehrana.dao;

import jakarta.persistence.*;
import org.example.mehrana.entity.Personnel;
import org.example.mehrana.exception.DuplicateNationalCodeException;

public class PersonnelDao {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("personnelUnit");
    private final EntityManager entityManager;

    public PersonnelDao() {
        this.entityManager = entityManagerFactory.createEntityManager();
    }

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
        Long count = entityManager.createQuery(
                        "SELECT COUNT(p) FROM Personnel p WHERE p.nationalCode = :nationalCode", Long.class)
                .setParameter("nationalCode", nationalCode)
                .getSingleResult();
        return count > 0;
    }
}
