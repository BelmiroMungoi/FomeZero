package com.bbm.fomezero.model;

import com.bbm.fomezero.model.enums.Role;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "users")
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String fullName;
    private String email;
    private String password;
    
    @Enumerated(EnumType.STRING)
    private Role role;
    
    private boolean status;
    
    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Profile profile;
    
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Address> addresses = new ArrayList<>();
    
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Cart cart;
    
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Order> orders = new ArrayList<>();
    
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
    private List<Review> reviews = new ArrayList<>();
    
    @OneToOne(mappedBy = "owner")
    private Restaurant restaurant;
    
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY)
    private Driver driver;

    /*TODO: Add the user favorites restaurants*/

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
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
        return status;
    }
}