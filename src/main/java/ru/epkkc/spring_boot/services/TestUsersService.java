package ru.epkkc.spring_boot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.epkkc.spring_boot.dao.RolesDao;
import ru.epkkc.spring_boot.dao.UsersDao;
import ru.epkkc.spring_boot.model.Role;
import ru.epkkc.spring_boot.model.RolesEnum;
import ru.epkkc.spring_boot.model.User;


import javax.annotation.PostConstruct;
import java.util.Arrays;

@Service
public class TestUsersService {

    private final UsersDao userDao;
    private final RolesDao rolesDao;

    @Autowired
    public TestUsersService(UsersDao dao, RolesDao rolesDao) {
        this.userDao = dao;
        this.rolesDao = rolesDao;
    }

    @PostConstruct
    public void postConstruct() {
        Role roleUser = new Role(RolesEnum.USER);
        Role roleAdmin = new Role(RolesEnum.ADMIN);
        rolesDao.save(roleUser);
        rolesDao.save(roleAdmin);


        userDao.save(new User("name1", "lastname1",
                (short) 2001,
                Arrays.asList(roleUser),
                "username1@mail.ru",
                "password1",
                true));
        userDao.save(new User("name2", "lastname2",
                (short) 2002,
                Arrays.asList(roleUser,
                        roleAdmin),
                "username2@mail.ru",
                "password2",
                true));
        userDao.save(new User("name3", "lastname3",
                (short) 2003,
                Arrays.asList(roleAdmin),
                "username3@mail.ru",
                "password3",
                true));
        userDao.save(new User("name4", "lastname4",
                (short) 2004,
                Arrays.asList(roleUser),
                "username4@mail.ru",
                "password4",
                false));
    }
}
