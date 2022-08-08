package com.monstarlab.webuser.service;

import com.monstarlab.webuser.model.User;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface UserService {
    Optional<User> findUserByUsername(String username);

    Optional<User> findUserByEmail(String email);

    Optional<User> findUserById(Long id);

    void deleteUser(Long id);

    User saveUser(User user);

    Page<User> getAllUsers(Integer page, Integer size, String sortable);

    Page<User> findUsersByUsernameStartingWith(Integer page, Integer size, String sortable, String keyUsername);
}
