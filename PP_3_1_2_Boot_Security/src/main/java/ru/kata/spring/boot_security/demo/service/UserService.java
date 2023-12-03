package ru.kata.spring.boot_security.demo.service;

import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;

import java.util.List;

public interface UserService {
    User getUserByUsername(String name);
    void updateUser(User user);
    void removeUser(long id);
    User getUserById(long id);
    void addUser(User user);
    List<User> allUser();
    public List <Role> getRoles();
}
