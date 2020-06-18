package com.przeskocz.AdvertEduPortal.DAO;

import com.przeskocz.AdvertEduPortal.model.Image;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface ImageDAO extends CrudRepository<Image, Long> {
    Optional<Image> findByAdvertisement_Id(Long advertisementId);
}
