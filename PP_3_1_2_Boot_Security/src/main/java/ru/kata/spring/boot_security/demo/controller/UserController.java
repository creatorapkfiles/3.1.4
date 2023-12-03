package ru.kata.spring.boot_security.demo.controller;

import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.service.UserService;

import java.security.Principal;
@Controller
@RequestMapping("/")
public class UserController {
    private final UserService us;

    public UserController(UserService us) {
        this.us = us;
    }

    @GetMapping("admin")
    public String allUsers(Model model, Principal principal) {
        User user1 = us.getUserByUsername(principal.getName());
        model.addAttribute("user", user1);
        model.addAttribute("users", us.allUser());
        model.addAttribute("newUser", new User());
        return "fixed";
    }

    @PostMapping("admin/add")
    public String add(@ModelAttribute("newUser") @Valid User user, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "fixed";
        } else {
            us.addUser(user);
            return "redirect:/admin";
        }
    }

    @GetMapping("user")
    public String getUser(Model model, Principal principal) {
        User user = us.getUserByUsername(principal.getName());
        model.addAttribute("user", user);
        return "user";
    }

    @PostMapping("admin/user")
    public String get(User user) {
        return "redirect:/admin/";
    }

    @GetMapping("admin/del")
    public String removeUser(@RequestParam("id") long id, RedirectAttributes ra) {
        us.removeUser(id);
        return "redirect:/admin";
    }

    @PostMapping("admin/edit")
    public String update(@ModelAttribute("newUser")@Valid User user, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "fixed";
        } else {
            us.updateUser(user);
            return "redirect:/admin";
        }
    }
}
