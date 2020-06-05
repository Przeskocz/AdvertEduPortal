package com.przeskocz.AdvertEduPortal.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"users"})
@Entity
public class Role {
    @Id
    @NonNull
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

    public boolean addUser(User u) {
        if (users == null)
            users = new ArrayList<>();
        if (!users.contains(u)) {
            users.add(u);
            return true;
        }
        return false;
    }
}
