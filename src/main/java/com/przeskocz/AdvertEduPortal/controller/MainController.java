package com.przeskocz.AdvertEduPortal.controller;

import com.przeskocz.AdvertEduPortal.DAO.*;
import com.przeskocz.AdvertEduPortal.model.*;
import com.przeskocz.AdvertEduPortal.model.user.*;
import com.przeskocz.AdvertEduPortal.service.CategoryService;
import com.przeskocz.AdvertEduPortal.service.CityService;
import com.przeskocz.AdvertEduPortal.service.UniversityService;
import com.przeskocz.AdvertEduPortal.service.UserAndRoleService;
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
    private final UserAndRoleService userAndRoleService;
    private final CategoryService categoryService;
    private final CityService cityService;
    private final UniversityService universityService;

    @Autowired
    public MainController(AdvertisementDAO advertisementDAO, CategoryDAO categoryDAO, CityDAO cityDAO, UniversityDAO universityDAO, UserDAO userDAO, RoleDAO roleDAO, UserAndRoleService userAndRoleService, CategoryService categoryService, CityService cityService, UniversityService universityService) {
        this.advertisementDAO = advertisementDAO;
        this.categoryDAO = categoryDAO;
        this.cityDAO = cityDAO;
        this.universityDAO = universityDAO;
        this.userDAO = userDAO;
        this.roleDAO = roleDAO;
        this.userAndRoleService = userAndRoleService;
        this.categoryService = categoryService;
        this.cityService = cityService;
        this.universityService = universityService;
    }

    @GetMapping("/")
    public Advertisement getSimpleAdvertisement(){
        Category cat1 = categoryService.findOrCreate(new Category(-1L, "Książki"));
        cat1 = categoryDAO.save(cat1);

        City city1 = cityService.findOrCreate(new City(-1L, "Rzeszów"));
        city1 = this.cityDAO.save(city1);

        University univ1 = universityService.findOrCreate(new University(-1L, "Politechnika Rzeszowska im. Ignecego Łukasiewicza", "PRz", city1));
        univ1 = this.universityDAO.save(univ1);

        User u1 = userAndRoleService.findOrCreateUser(new User(-1L, "Przeskocz", "skoczp@gmail.com", "qwerty", 1));

        Role r1 = userAndRoleService.findOrCreateRole(new Role(-1L, UserRoleEnum.ADMIN));
        if (r1.addUser(u1))
            this.roleDAO.save(r1);

        Advertisement adv = new Advertisement();
        adv.setId(-1L);
        adv.setTitle("Testowe ogłoszenie");
        adv.setDescription("To jest ogłoszenie testowe. Sprawdzamy czy serwer działa poprawnie :)");
        adv.setPrice(99.99);
        adv.setStartDate(LocalDateTime.now());
        adv.setExpirationDate(LocalDateTime.now().plusDays(14));
        adv.setImages(new ArrayList<>());

        adv.setCategory(cat1);
        adv.setCity(city1);
        adv.setUniversity(univ1);
        adv.setUser(u1);
        adv = this.advertisementDAO.save(adv);

        return adv;
    }
}
