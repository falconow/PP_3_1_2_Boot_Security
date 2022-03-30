package ru.kata.spring.boot_security.demo.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.kata.spring.boot_security.demo.dao.UserDao;
import ru.kata.spring.boot_security.demo.model.User;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    private final UserDao userDao;
    private final PasswordEncoder encoder;


    @Autowired
    public UserServiceImpl(UserDao userDao, PasswordEncoder encoder) {
        this.userDao = userDao;
        this.encoder = encoder;
    }

    @Override
    public void addUser(User user) {
        String password = user.getPassword();
        user.setPassword(encoder.encode(password));
        userDao.addUser(user);
    }

    @Override
    public void deleteUser(Long id) {
        userDao.deleteUser(id);
    }

    @Override
    public void updateUser(User user) {
        String pass = user.getPassword();
        if (pass.isEmpty()) {
            String password = userDao.findUserById(user.getId()).getPassword();
            user.setPassword(password);
        } else {
            user.setPassword(encoder.encode(user.getPassword()));
        }
        userDao.updateUser(user);
    }

    @Override
    public User findUserById(Long id) {
        return userDao.findUserById(id);
    }

    @Override
    public User findUserByLogin(String login) {
        return userDao.findUserByLogin(login);
    }

    @Override
    public List<User> getAllUser() {
        return userDao.getAllUser();
    }


}
