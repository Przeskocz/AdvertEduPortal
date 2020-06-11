package com.przeskocz.AdvertEduPortal.controller;

import com.przeskocz.AdvertEduPortal.model.user.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import com.przeskocz.AdvertEduPortal.service.*;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
public class MainController extends CommonController{
    private final UserAndRoleService userAndRoleService;
    private final CategoryService categoryService;
    private final CityService cityService;
    private final UniversityService universityService;
    private final AdvertisementService advertisementService;

    @Autowired
    public MainController(UserAndRoleService userAndRoleService, CategoryService categoryService, CityService cityService, UniversityService universityService, AdvertisementService advertisementService) {
        this.userAndRoleService = userAndRoleService;
        this.categoryService = categoryService;
        this.cityService = cityService;
        this.universityService = universityService;
        this.advertisementService = advertisementService;
    }

    @GetMapping({"/", "/home"})
    public String homePage(Model model) {
        buildMyModel(model);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = new User();
        if (auth != null)
            user = userAndRoleService.findUserByEmail(auth.getName());

        model.addAttribute("user", user);
        model.addAttribute("newAdvertisementsList", advertisementService.getAllNewAdvertisements());
        model.addAttribute("countUniversiteis", commonService.countAllUniversities());
        model.addAttribute("countStudents", userAndRoleService.countAllUsers());
        model.addAttribute("countCategories", commonService.countAllCategories());
        model.addAttribute("countAdvertisements", advertisementService.getAllActualAdvertisements().size());
        return "index";
    }
}
