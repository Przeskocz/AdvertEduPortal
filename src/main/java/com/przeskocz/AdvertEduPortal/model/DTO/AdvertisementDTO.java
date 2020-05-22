package com.przeskocz.AdvertEduPortal.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvertisementDTO {
    private Long id;
    private String title;
    private String description;
    private LocalDateTime expirationDate;
    private Double price;
    private LocalDateTime startDate;
    private CityDTO city;
    private CategoryDTO category;
    private UniversityDTO university;
    private List<ImageDTO> images;
    private UserDTO userDTO;
}
