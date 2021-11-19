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
//        roleDao.addRole(new Role(RolesEnum.USER));
//        roleDao.addRole(new Role(RolesEnum.ADMIN));


        userDao.save(new User("name1", "lastname1",
                (short) 2001,
                Arrays.asList(new Role(RolesEnum.USER)),
                "username1",
                "password1",
                true));
        userDao.save(new User("name2", "lastname2",
                (short) 2002,
                Arrays.asList(new Role(RolesEnum.USER),
                        new Role(RolesEnum.ADMIN)),
                "username2",
                "password2",
                true));
        userDao.save(new User("name3", "lastname3",
                (short) 2003,
                Arrays.asList(new Role(RolesEnum.ADMIN)),
                "username3",
                "password3",
                true));
        userDao.save(new User("name4", "lastname4",
                (short) 2004,
                Arrays.asList(new Role(RolesEnum.USER)),
                "username4",
                "password4",
                false));
    }
}
