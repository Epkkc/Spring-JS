package ru.epkkc.spring_boot.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.epkkc.spring_boot.dao.UsersDao;
import ru.epkkc.spring_boot.model.User;

@Service("userDetailService")
public class UserDetailServiceImpl implements UserDetailsService {

    private final UsersDao usersDao;

    @Autowired
    public UserDetailServiceImpl(UsersDao usersDao) {
        this.usersDao = usersDao;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (username == null) {
            throw new UsernameNotFoundException("Username is null");
        }
        User user = usersDao.findByUsername(username);
        if (user != null) {
            return user;
        } else {
            throw new UsernameNotFoundException("There are no user with username = " + username);
        }
    }
}
