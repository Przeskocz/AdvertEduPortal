package com.przeskocz.AdvertEduPortal.controller;

import com.przeskocz.AdvertEduPortal.DAO.*;
import com.przeskocz.AdvertEduPortal.model.*;
import com.przeskocz.AdvertEduPortal.model.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.ArrayList;

@RestController
@ResponseBody
public class MainController {
    private final AdvertisementDAO advertisementDAO;
    private final CategoryDAO categoryDAO;
    private final CityDAO cityDAO;
    private final RoleDAO roleDAO;
    private final UniversityDAO universityDAO;
    private final UserDAO userDAO;

    @Autowired
    public MainController(AdvertisementDAO advertisementDAO, CategoryDAO categoryDAO, CityDAO cityDAO, UniversityDAO universityDAO, UserDAO userDAO, RoleDAO roleDAO) {
        this.advertisementDAO = advertisementDAO;
        this.categoryDAO = categoryDAO;
        this.cityDAO = cityDAO;
        this.universityDAO = universityDAO;
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
    }

    @GetMapping("/")
    public Advertisement getSimpleAdvertisement(){
        Category cat1 = new Category(-1L, "Książki");
        cat1 = this.categoryDAO.save(cat1);

        City city1 = new City(-1L, "Rzeszów");
        city1 = this.cityDAO.save(city1);

        University univ1 = new University(-1L, "Politechnika Rzeszowska im. Ignecego Łukasiewicza", "PRz", city1);
        univ1.setCity(city1);
        univ1 = this.universityDAO.save(univ1);

        User u1 = new User(-1L, "Przeskocz", "skoczp@gmail.com", "qwerty", 1);
        u1 = this.userDAO.save(u1);

        Role r1 = new Role(-1L, UserRoleEnum.ADMIN);
        r1.addUser(u1);
        r1 = this.roleDAO.save(r1);

        Advertisement obj = new Advertisement();
        obj.setId(-1L);
        obj.setTitle("Testowe ogłoszenie");
        obj.setDescription("To jest ogłoszenie testowe. Sprawdzamy czy serwer działa poprawnie :)");
        obj.setPrice(99.99);
        obj.setStartDate(LocalDateTime.now());
        obj.setExpirationDate(LocalDateTime.now().plusDays(14));
        obj.setImages(new ArrayList<>());

        obj.setCategory(cat1);
        obj.setCity(city1);
        obj.setUniversity(univ1);
        obj.setUser(u1);
        obj = this.advertisementDAO.save(obj);

        return obj;
    }
}
