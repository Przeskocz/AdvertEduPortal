package com.przeskocz.AdvertEduPortal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"advertisements", "universities"})
@Entity
public class City {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    @Column(unique = true)
    private String name;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "city")
    private List<Advertisement> advertisements;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "city")
    private List<University> universities;


    public City(String name) {
        this(-1L, name);
    }

    public City(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
