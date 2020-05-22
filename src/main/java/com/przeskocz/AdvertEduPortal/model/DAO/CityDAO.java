package com.przeskocz.AdvertEduPortal.model.DAO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "city")
@Entity
public class CityDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "city")
    private List<AdvertisementDAO> advertisements;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "city")
    private List<UniversityDAO> universities;
}
