package com.przeskocz.AdvertEduPortal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"advertisements"})
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private Integer active;
    @NonNull
    private String name;
    @NonNull
    private String email;
    @NonNull
    private String password;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "role_user",
            joinColumns = {@JoinColumn(name = "user.id")},
            inverseJoinColumns = {@JoinColumn(name = "role.id")}
    )
    private Set<Role> roles;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @ToString.Exclude
    private List<Advertisement> advertisements;

    public User(Integer active, String name, String email, String password) {
        this(-1L, active, name, email, password);
    }

    public User(Long id, Integer active, String name, String email, String password) {
        this.id = id;
        this.active = active;
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public boolean addRole(Role role) {
        if (roles == null)
            roles = new HashSet<>();
        return roles.add(role);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(name, user.name) &&
                Objects.equals(email, user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email);
    }
}
