package com.bugtracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties; // added on 17/12/25
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import com.bugtracker.entity.Bug;
import java.io.Serializable;

import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true) // added on 17/12/25

public class User implements UserDetails, Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @JsonIgnore
    @OneToMany(mappedBy = "assignedTo")
    private List<Bug> assignedBugs;

    // ADD THESE CONSTRUCTORS
    public User(Long id, String username, String password, Role role) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.role = role;
    }

    // ----------------------------
    // UserDetails required methods
    // ----------------------------

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
    }

    // comment added on 17.12.25
    /*@Override
    public String getPassword()
     {
        return password;
    }

    @Override
    public String getUsername()
     {
        return username;
    }
     */

    @Override
    @JsonIgnore     // added on 17/12
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore     // added on 17/12
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore     // added on 17/12
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore     // added on 17/12
    public boolean isEnabled() {
        return true;
    }
}
