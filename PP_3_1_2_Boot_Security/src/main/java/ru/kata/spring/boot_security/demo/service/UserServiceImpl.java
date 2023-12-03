package ru.kata.spring.boot_security.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.kata.spring.boot_security.demo.model.Role;
import ru.kata.spring.boot_security.demo.model.User;
import ru.kata.spring.boot_security.demo.repository.RoleRepository;
import ru.kata.spring.boot_security.demo.repository.UserRepository;

import java.util.Collections;
import java.util.List;
@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public User getUserByUsername(String name) {
        return userRepository.findByUsername(name).orElse(null);
    }

    @Override
    public void updateUser(User user) {
        User user1 = getUserById(user.getId());
        if (user.getRoles().hashCode() == 0) {
            user.setRoles(user1.getRoles());
        }
        if (user.getPassword().hashCode() == user1.getPassword().hashCode()) {
            user.setPassword(user.getPassword());
        } else {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        }
        userRepository.save(user);
    }

    @Override
    public void removeUser(long id) {
        userRepository.delete(getUserById(id));
    }

    @Override
    public User getUserById(long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public void addUser(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        if (user.getRoles().hashCode() == 0) {
            user.setRoles(Collections.singleton(new Role(2L,"USER")));
        }
        userRepository.save(user);
    }

    @Override
    public List<User> allUser() {
        return userRepository.findAll();
    }

    @Override
    public List<Role> getRoles() {
        return roleRepository.findAll();
    }
}
