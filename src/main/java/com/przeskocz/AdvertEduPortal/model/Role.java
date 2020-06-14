package com.przeskocz.AdvertEduPortal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"users"})
@Entity
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @NonNull
    @Column(unique = true)
    @Enumerated(EnumType.STRING)
    private UserRoleEnum role;

    @ManyToMany(cascade = {CascadeType.ALL})
    @JoinTable(
            name = "role_user",
            joinColumns = {@JoinColumn(name = "role.id")},
            inverseJoinColumns = {@JoinColumn(name = "user.id")}
    )
    @ToString.Exclude
    private List<User> users;

    public Role(UserRoleEnum role) {
        this(-1L, role);
    }

    public Role(Long id, UserRoleEnum role) {
        this.id = id;
        this.role = role;
    }

    public boolean addUser(User u) {
        if (users == null)
            users = new ArrayList<>();
        if (!users.contains(u)) {
            users.add(u);
            return true;
        }
        return false;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role1 = (Role) o;
        return Objects.equals(id, role1.id) &&
                role == role1.role;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, role);
    }
}
