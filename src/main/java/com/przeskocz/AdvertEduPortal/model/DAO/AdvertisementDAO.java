package com.przeskocz.AdvertEduPortal.model.DAO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "advertisement")
@Entity
public class AdvertisementDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    private LocalDateTime expirationDate;
    private Double price;
    private LocalDateTime startDate;

    @JoinColumn(name = "id")
    @ManyToOne()
    private CityDAO city;

    @JoinColumn(name = "id")
    @ManyToOne()
    private CategoryDAO category;

    @JoinColumn(name = "id")
    @ManyToOne()
    private UniversityDAO university;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "advertisement")
    private List<ImageDAO> images;

    //TODO User / Owner
}
