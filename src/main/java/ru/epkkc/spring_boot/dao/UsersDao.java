package ru.epkkc.spring_boot.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.epkkc.spring_boot.model.User;

import java.util.Optional;

@Repository
public interface UsersDao extends JpaRepository<User, Long> {
    User findByUsername(String username);
    Optional<User> findById(Long id);
}
