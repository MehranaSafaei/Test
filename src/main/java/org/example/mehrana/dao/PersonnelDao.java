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
    public void create(Personnel entity) throws DuplicateNationalCodeException {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            if (isNationalIdDuplicated(entity.getNationalCode())) {
                throw new DuplicateNationalCodeException("National code is duplicated!");
            }
            entityTransaction.begin();
            entityManager.persist(entity);
            entityTransaction.commit();
        } catch (Exception e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw e;
        }
    }

    public Optional<Personnel> findById(Long id) {
        return Optional.ofNullable(entityManager.find(Personnel.class, id));
    }

    public void update(Personnel entity) throws DuplicateNationalCodeException {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            if (isNationalIdDuplicated(entity.getNationalCode()) && !isSamePersonnel(entity)) {
                throw new DuplicateNationalCodeException();
            }
            entityTransaction.begin();
            entityManager.merge(entity);
            entityTransaction.commit();
        } catch (Exception e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw e;
        }
    }

    public boolean isNationalIdDuplicated(long nationalCode) {
        Long count = entityManager.createNamedQuery(
                        "CountByNationalCode", Long.class)
                .setParameter("nationalCode", nationalCode)
                .getSingleResult();
        return count > 0;
    }

    //checking that the nationalCode belongs to the same personnel
    private boolean isSamePersonnel(Personnel personnel) {
        Optional<Personnel> existingPersonnel = findByNationalCode(personnel.getNationalCode());
        return existingPersonnel.map(p -> p.getId().equals(personnel.getId())).orElse(false);
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

    public void deleteByNationalCode(Long nationalCode) {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            Optional<Personnel> personnel = findByNationalCode(nationalCode);
            if (personnel.isPresent()) {
                entityTransaction.begin();
                entityManager.remove(personnel.get());
                entityTransaction.commit();
            }
        } catch (Exception e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw e;
        }
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

    public List<Personnel> findAll() {
        return entityManager.createNamedQuery("SelectAll", Personnel.class).getResultList();
    }

}
