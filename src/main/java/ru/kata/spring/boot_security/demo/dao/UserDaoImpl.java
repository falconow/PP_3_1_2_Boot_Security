package ru.kata.spring.boot_security.demo.dao;


import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.User;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public class UserDaoImpl implements UserDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void addUser(User user) {
        entityManager.persist(user);
    }

    @Override
    public void deleteUser(Long id) {
       entityManager.createQuery("DELETE FROM User WHERE id = :id")
               .setParameter("id", id)
               .executeUpdate();
    }

    @Override
    public void updateUser(User user) {
        entityManager.merge(user);
    }

    @Override
    public User findUserById(Long id) {
       return entityManager.find(User.class, id);
    }

    @Override
    public User findUserByLogin(String login) {
        User user = entityManager.createQuery("SELECT user from User user where user.login = :login", User.class)
                .setParameter("login", login)
                .getSingleResult();
        return user;
    }

    @Override
    public List<User> getAllUser() {
        List<User> result = entityManager.createQuery("SELECT user from User user", User.class).getResultList();
        return result;
    }
}
