package com.monstarlab.webuser.service.impl;

import com.monstarlab.webuser.model.User;
import com.monstarlab.webuser.repository.UserRepository;
import com.monstarlab.webuser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userRepository.findUserByEmail(email);
    }

    @Override
    public Optional<User> findUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User updateUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public Page<User> getAllUsers(Integer page, Integer size, String sort) {
        Pageable pageable = getPageable(page, size, sort);
        return userRepository.findAll(pageable);
    }

    @Override
    public Page<User> findUsersByUsernameStartingWith(Integer page, Integer size, String sort, String keyUsername) {
        Pageable pageable = getPageable(page, size, sort);
        return userRepository.findUsersByUsernameStartingWith(pageable, keyUsername);
    }

    private Pageable getPageable(Integer page, Integer size, String sort) {
        page = page < 0 ? 0 : page;
        Sort sortable = Sort.by("id").ascending();
        if (sort.equals("DESC")) {
            sortable = Sort.by("id").descending();
        }
        return PageRequest.of(page, size, sortable);
    }
}
