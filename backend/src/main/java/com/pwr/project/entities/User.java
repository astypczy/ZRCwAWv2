package com.pwr.project.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String cognitoSub;
    private String email;
    private String firstName;
    private String surname;
    private boolean isSeller;
    private String login;

    public User(String firstName, String surname, String login, String email, Boolean isSeller) {
        this.firstName = firstName;
        this.surname = surname;
        this.login = login;
        this.email = email;
        this.isSeller = isSeller != null ? isSeller : false;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(isSeller ? "ROLE_SELLER" : "ROLE_USER"));
    }

    @Override
    public String getPassword() {
        return null; // Password is handled by Cognito
    }

    @Override
    public String getUsername() {
        return this.login;
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

    public Boolean getIsSeller() {
        return this.isSeller;
    }
}
