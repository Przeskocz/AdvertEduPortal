package com.przeskocz.AdvertEduPortal.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class University implements Comparable<University> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String shortName;

    @ManyToOne()
    private City city;

    public University(String name, String shortName, City city) {
        this(-1L, name, shortName, city);
    }

    @Override
    public int compareTo(University o) {
        return this.getName().compareTo(o.getName());
    }
}
