package com.monstarlab.webuser.controller;

import com.monstarlab.webuser.model.User;
import com.monstarlab.webuser.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping({"/", "/home", "/search"})
    public String home(Model model,
                       @RequestParam(name = "keyword", required = false) String keyword,
                       @RequestParam(name = "page", required = false, defaultValue = "0") Integer page,
                       @RequestParam(name = "size", required = false, defaultValue = "7") Integer size,
                       @RequestParam(name = "sort", required = false, defaultValue = "ASC") String sort) {
        page = page < 0 ? 0 : page;
        Sort sortable = Sort.by("id").ascending();;
        if (sort.equals("DESC")) {
            sortable = Sort.by("id").descending();
        }
        Pageable pageable = PageRequest.of(page, size, sortable);
        if (keyword != null) {
            Page<User> userList = userService.findUsersByUsernameStartingWith(pageable, keyword);
            model.addAttribute("users", userList);
            model.addAttribute("keyword", keyword);
        } else {
            Page<User> userList = userService.getAllUsers(pageable);
            model.addAttribute("users", userList);
        }
        return "index";
    }

    @GetMapping("/edit")
    public String editUser(Model model, @RequestParam Long id) {
        Optional<User> user = userService.findUserById(id);
        model.addAttribute("user", user);
        return "edit";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") User user, Model model) {
        userService.saveUser(user);
        return "redirect:home";
    }

    @GetMapping("/delete")
    public String deleteUser(@RequestParam Long id) {
        User user = userService.findUserById(id).orElseThrow(() -> new IllegalArgumentException("Không không tồn tại username = " + id));
        userService.deleteUser(id);
        return "redirect:home";
    }

    @GetMapping("/create")
    public String createUser(@ModelAttribute("user") User user, Model model, BindingResult bindingResult) {
        return "create";
    }

    @PostMapping("/create")
    public String saveUser(@Valid User user, BindingResult bindingResult, Model model) {
        Optional<User> user1 = userService.findUserByUsername(user.getUsername());
        String messageUsername = user1.isPresent() ? "Username đã tồn tại" : "";
        Optional<User> user2 = userService.findUserByEmail(user.getEmail());
        String messageEmail = user2.isPresent() ? "Email đã tồn tại" : "";
        String messageSuccess = "";
        if (user1.isPresent() || user2.isPresent()) {
            messageSuccess = "Đăng ký thất bại";
        } else {
            userService.saveUser(user);
            System.out.println(user);
            messageSuccess = "Đăng ký thành công";
        }
        System.out.println("email: " + messageEmail);
        System.out.println("success: " + messageSuccess);
        System.out.println("username: " + messageUsername);
        model.addAttribute("messageUsername", messageUsername);
        model.addAttribute("messageEmail", messageEmail);
        model.addAttribute("messageSuccess", messageSuccess);
        return "create";
    }
}
