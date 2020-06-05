package com.przeskocz.AdvertEduPortal.model;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@NoArgsConstructor
@Table
@Entity
public class City {
    @Id
    @NonNull
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NonNull
    private String name;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "city")
    private List<Advertisement> advertisements;

    @ToString.Exclude
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "city")
    private List<University> universities;
}
