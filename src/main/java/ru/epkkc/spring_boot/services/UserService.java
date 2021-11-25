package ru.epkkc.spring_boot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.epkkc.spring_boot.dao.UsersDao;
import ru.epkkc.spring_boot.model.User;

import java.util.List;

@Transactional
@Service
public class UserService implements UserServiceInt{

    private UsersDao userDao;

    @Autowired
    public UserService(UsersDao userDao) {
        this.userDao = userDao;
    }

    @Override
    public void update(User user) {
        User userForUpdate = findById(user.getId());

        if (!user.getName().isEmpty()) userForUpdate.setName(user.getName());
        if (!user.getLastname().isEmpty()) userForUpdate.setLastname(user.getLastname());
        if (user.getYearOfBirth() > 0) userForUpdate.setYearOfBirth(user.getYearOfBirth());
        if (!user.getUsername().isEmpty()) userForUpdate.setUsername(user.getUsername());
        if (!user.getPassword().isEmpty()) userForUpdate.setPassword(user.getPassword());
        if (!user.getRoles().isEmpty()) userForUpdate.setRoles(user.getRoles());
        if (user.getIsActive() != null) userForUpdate.setIsActive(user.getIsActive());

        userDao.save(userForUpdate);
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public void save(User user) {
        userDao.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userDao.findByUsername(username);
    }

    @Override
    public User findById(Long id) {
        return userDao.findById(id).get();
    }

    @Override
    public void deleteById(Long id) {
        userDao.deleteById(id);
    }

}
