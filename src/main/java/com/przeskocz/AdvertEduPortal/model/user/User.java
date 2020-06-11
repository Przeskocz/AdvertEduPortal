package com.przeskocz.AdvertEduPortal.model.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.przeskocz.AdvertEduPortal.model.Advertisement;
import lombok.*;

import javax.persistence.*;
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
    private String name;
    @NonNull
    private String email;
    @NonNull
    private String password;

    @ManyToMany(mappedBy = "users")
    private Set<Role> roles;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "user")
    @ToString.Exclude
    private List<Advertisement> advertisements;

    public User(String name, String email, String password) {
        this(-1L, name, email, password);
    }

    public User(Long id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
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
