package com.przeskocz.AdvertEduPortal.model;

import com.przeskocz.AdvertEduPortal.model.DTO.AdvertisementDTO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Advertisement implements Comparable<Advertisement> {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String description;

    private LocalDateTime expirationDate;
    private BigDecimal price;
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

    public boolean isActive() {
        return getExpirationDate().compareTo(LocalDateTime.now()) >= 0;
    }

    @Override
    public int compareTo(Advertisement o) {
        if (this.startDate == null)
            return 1;
        if (o.startDate == null)
            return -1;
        return o.getStartDate().compareTo(this.startDate);
    }

    public AdvertisementDTO toDTO() {
        AdvertisementDTO dto = new AdvertisementDTO();
        dto.setId(this.getId());
        dto.setTitle(this.getTitle());
        dto.setDescription(this.getDescription());
        dto.setPrice(this.getPrice());
        dto.setExpirationDate(Date.from(this.getExpirationDate().atZone(ZoneId.systemDefault()).toInstant()));
        dto.setListOfImages(this.getImages());

        return dto;
    }

    public String getFormattedStartDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return getStartDate().format(formatter);
    }

    public String getFormattedExpirationDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        return getExpirationDate().format(formatter);
    }
}
