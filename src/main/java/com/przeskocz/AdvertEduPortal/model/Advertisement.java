package com.przeskocz.AdvertEduPortal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.przeskocz.AdvertEduPortal.model.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    private LocalDateTime expirationDate;
    private Double price;
    private LocalDateTime startDate;

    @ManyToOne()
    private City city;

    @ManyToOne()
    private Category category;

    @ManyToOne()
    private University university;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "advertisement")
    private List<Image> images;

    @ManyToOne()
    private User user;
}
