package com.przeskocz.AdvertEduPortal.model.DTO;

import com.przeskocz.AdvertEduPortal.model.Image;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AdvertisementDTO {
    private Long id;

    @NotNull
    @NotEmpty
    private String title;

    @NotNull
    private String description;

    private BigDecimal price;

    @NotNull
    private Long categoryId;

    private Long universityId;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date expirationDate;

    // Only for adding form
    private List<MultipartFile> images;

    // Only for editing form
    private List<Image> listOfImages;
}
