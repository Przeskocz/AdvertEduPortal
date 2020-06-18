package com.przeskocz.AdvertEduPortal.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.sql.Timestamp;


@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"advertisement"})
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String alt;
    private String path;
    private String src;
    private Timestamp timestamp;

    @ToString.Exclude
    @ManyToOne()
    private Advertisement advertisement;
}
