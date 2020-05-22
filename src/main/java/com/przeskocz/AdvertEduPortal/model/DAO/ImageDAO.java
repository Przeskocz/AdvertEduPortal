package com.przeskocz.AdvertEduPortal.model.DAO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "image")
@Entity
public class ImageDAO {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String alt;
    private String path;
    private String src;

    @ToString.Exclude
    @JoinColumn(name = "id")
    @ManyToOne()
    private AdvertisementDAO advertisement;
}
