package com.przeskocz.AdvertEduPortal.service;

import com.przeskocz.AdvertEduPortal.DAO.CityDAO;
import com.przeskocz.AdvertEduPortal.model.City;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CityService {
    private final CityDAO cityDAO;

    @Autowired
    public CityService(CityDAO cityDAO) {
        this.cityDAO = cityDAO;
    }

    public City findOrCreate(City city) {
        if (city == null)
            throw new NullPointerException("The city param cannot be null!");

        City result = cityDAO.findById(city.getId()).orElse(null);
        if (result == null)
            result = cityDAO.findByNameContainingIgnoreCase(city.getName()).orElse(null);

        if (result == null)
            result = cityDAO.save(city);

        return result;
    }

    public City save(City c) {
        return cityDAO.save(c);
    }
}
