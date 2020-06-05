package com.przeskocz.AdvertEduPortal.service;

import com.przeskocz.AdvertEduPortal.DAO.UniversityDAO;
import com.przeskocz.AdvertEduPortal.model.University;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UniversityService {
    private final UniversityDAO universityDAO;

    @Autowired
    public UniversityService(UniversityDAO universityDAO) {
        this.universityDAO = universityDAO;
    }

    public University findOrCreate(University university) {
        if (university == null)
            throw new NullPointerException("The university param cannot be null!");
        University result = universityDAO.findById(university.getId()).orElse(null);
        if (result == null)
            result =universityDAO.findByNameContainingIgnoreCase(university.getName()).orElse(null);
        if (result == null)
            result = universityDAO.save(university);

        return result;
    }
}
