package com.example.jwt_auth_springboot.Entity;

import jakarta.persistence.*;
import com.example.jwt_auth_springboot.Enums.Role;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

/**
 * User entity class representing a user in the system.
 */
@Entity
@Data
@Builder
@Table(name = "user_jwt", schema = "public")
@AllArgsConstructor
@NoArgsConstructor
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    // --- UserDetails contract methods ---

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // If roles or permissions needed, return them here
        return Collections.emptyList();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;  // Customize if needed
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;  // Customize if needed
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;  // Customize if needed
    }

    @Override
    public boolean isEnabled() {
        return true;  // Customize if needed
    }
}
