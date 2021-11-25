package ru.epkkc.spring_boot.services;

import ru.epkkc.spring_boot.dao.RolesDao;
import ru.epkkc.spring_boot.model.User;

import java.util.List;
import java.util.Optional;

public interface UserServiceInt {

    void update(User user);

    List<User> findAll();

    void save(User user);

    User findByUsername(String username);

    User findById(Long id);

    void deleteById(Long id);

}
