package com.przeskocz.AdvertEduPortal.DAO;

import com.przeskocz.AdvertEduPortal.model.City;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CityDAO extends CrudRepository<City, Long> {
    Optional<City> findByNameContainingIgnoreCase(String name);
}
