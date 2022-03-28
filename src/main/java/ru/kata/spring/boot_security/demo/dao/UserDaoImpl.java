package ru.kata.spring.boot_security.demo.dao;


import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void add(User user) {
        entityManager.persist(user);
    }

    @Override
    public void delete(Long id) {
       entityManager.createQuery("DELETE FROM User WHERE id = :id")
               .setParameter("id", id)
               .executeUpdate();
    }

    @Override
    public void update(User user) {
        entityManager.merge(user);
    }

    @Override
    public User findById(Long id) {
       return entityManager.find(User.class, id);
    }

    @Override
    public User findByLogin(String login) {
        User user = (User) entityManager.createQuery("SELECT user from User user where user.login = :login")
                .setParameter("login", login)
                .getSingleResult();
        return user;
    }

    @Override
    public List<User> getAll() {
        List<User> result = entityManager.createQuery("SELECT user from User user").getResultList();
        return result;
    }
}
