package org.example.mehrana.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Persistence;
import org.example.mehrana.entity.Leave;
import org.example.mehrana.entity.Personnel;
import org.example.mehrana.exception.SaveRecordException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class LeaveDao implements CrudDao<Leave> {

    private static final EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("Test");
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
    public Leave findById(Long id) {
        return entityManager.find(Leave.class, id);
    }

    @Override
    public void update(Leave entity) {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            entityTransaction.begin();
            entityManager.merge(entity);
            entityTransaction.commit();
        } catch (Exception e) {
            entityTransaction.rollback();
            e.printStackTrace();
        }
    }


    @Override
    public void delete(Long id) {
        EntityTransaction entityTransaction = entityManager.getTransaction();
        try {
            Leave leave = entityManager.find(Leave.class, id);
            if (leave != null) {
                entityTransaction.begin();
                entityManager.remove(leave);
                entityTransaction.commit();
            }
        } catch (Exception e) {
            if (entityTransaction.isActive()) {
                entityTransaction.rollback();
            }
            throw e;
        }
    }

    @Override
    public List<Leave> findAll() {
        return entityManager.createNamedQuery("selectAll", Leave.class).getResultList();
    }


    public List<Leave> findAllByUsername(String username) {
        return entityManager.createNamedQuery(
                        "findLeavesByPersonnelUsername", Leave.class)
                .setParameter("username", username)
                .getResultList();
    }

    public boolean existsByPersonnelId(Long personnelId) {
        Long count = entityManager.createNamedQuery("countExistsByPersonnelId", Long.class)
                .setParameter("personnelId", personnelId)
                .getSingleResult();
        return count > 0;
    }


    public Optional<Leave> findByPersonnelIdAndDateRange(long personnelId, LocalDate startDate, LocalDate endDate) {
        try {
            Leave leave = entityManager.createNamedQuery("selectByPersonnelIdAndDateRange", Leave.class)
                    .setParameter("personnelId", personnelId)
                    .setParameter("startDate", startDate)
                    .setParameter("endDate", endDate)
                    .getSingleResult();

            return Optional.ofNullable(leave);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public void deleteByPersonnelId(Long id) {
    }



    public List<Leave> findLeaveByPersonnelId(Long personnelId) {
        return entityManager.createNamedQuery("selectByPersonnelId", Leave.class)
                .setParameter("personnelId", personnelId)
                .getResultList(); // I made a mistake because getSingleResult I put
    }

}
