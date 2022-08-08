package com.monstarlab.webuser.repository;

import com.monstarlab.webuser.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByUsername(String username);

    Optional<User> findUserByEmail(String email);

    Page<User> findAll(Pageable pageable);

    Page<User> findUsersByUsernameStartingWith(Pageable pageable, String value);
}
