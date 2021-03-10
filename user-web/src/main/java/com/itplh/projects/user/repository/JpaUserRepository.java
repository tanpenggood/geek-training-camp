package com.itplh.projects.user.repository;

import com.itplh.projects.user.domain.User;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import java.util.Collection;

/**
 * @author: tanpenggood
 * @date: 2021-03-10 23:17
 */
public class JpaUserRepository implements UserRepository {

    @Resource(name = "bean/EntityManager")
    private EntityManager entityManager;

    @Override
    public boolean save(User user) {
        EntityTransaction transaction = entityManager.getTransaction();
        try {
            transaction.begin();
            entityManager.persist(user);
            transaction.commit();
            return true;
        } catch (Exception e) {
            transaction.rollback();
            return false;
        }
    }

    @Override
    public boolean deleteById(Long userId) {
        return false;
    }

    @Override
    public boolean update(User user) {
        return false;
    }

    @Override
    public User getById(Long userId) {
        return null;
    }

    @Override
    public User getByNameAndPassword(String name, String password) {
        return null;
    }

    @Override
    public User getByName(String name) {
        return null;
    }

    @Override
    public Collection<User> getAll() {
        return null;
    }
}
