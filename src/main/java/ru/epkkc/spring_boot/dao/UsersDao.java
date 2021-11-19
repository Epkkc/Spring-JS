package ru.epkkc.spring_boot.dao;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.epkkc.spring_boot.model.User;

import java.util.Optional;

@Repository
public interface UsersDao extends JpaRepository<User, Long> {

    User findByUsername(String username);

    Optional<User> findById(Long id);

    @Modifying
    @Query(value = "DELETE FROM User u WHERE  u.user_id = ?1")
    void deleteById(Long id);
}
