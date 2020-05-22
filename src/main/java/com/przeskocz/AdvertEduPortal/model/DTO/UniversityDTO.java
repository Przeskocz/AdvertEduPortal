package com.przeskocz.AdvertEduPortal.model.DTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UniversityDTO {
    private Long id;
    private String name;
    private String shortName;
    private CityDTO city;
}
