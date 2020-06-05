package com.przeskocz.AdvertEduPortal.DAO;

import com.przeskocz.AdvertEduPortal.model.University;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UniversityDAO extends CrudRepository<University, Long> {
    Optional<University> findByNameContainingIgnoreCase(String name);
}
