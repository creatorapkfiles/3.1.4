package ru.kata.spring.boot_security.demo.model;

import javax.persistence.*;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Data
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstName;
    private String lastName;
    private String age;
    private String email;
    private String password;
    @Transient
    private String passwordConfirm;
    @ManyToMany(fetch = FetchType.LAZY)
    private Set<Role> roles;

    public User() {
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return roles.stream().map(c -> new SimpleGrantedAuthority(c.getName().replace("[", "").replace("]", ", "))).collect(Collectors.toList());
    }
    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }
    public String getUserRole() {
        return getAuthorities().toString().replace("[","").replace("]","");
    }
}