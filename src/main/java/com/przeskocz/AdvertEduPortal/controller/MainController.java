package com.przeskocz.AdvertEduPortal.controller;

import com.przeskocz.AdvertEduPortal.model.*;
import com.przeskocz.AdvertEduPortal.model.DTO.UserDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import com.przeskocz.AdvertEduPortal.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.*;

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
        model.addAttribute("countUniversities", commonService.countAllUniversities());
        model.addAttribute("countStudents", userAndRoleService.countAllUsers());
        model.addAttribute("countCategories", commonService.countAllCategories());
        model.addAttribute("countAdvertisements", advertisementService.getAllActualAdvertisements().size());
        return "index";
    }

    @GetMapping({"/login", "/registration"})
    public String loginForm(Model model) {
        buildMyModel(model);
        model.addAttribute("accountDto", new UserDTO());
        model.addAttribute("logout", null);
        return "login";
    }

    @PostMapping("/registration")
    public ModelAndView registerUserAccount(@ModelAttribute("accountDto") @Valid UserDTO accountDto,
                                            BindingResult result) {
        if (!result.hasErrors()) {
            userAndRoleService.registerNewUserAccount(accountDto, result);
        }
        if (result.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("login", "accountDto", accountDto);
            buildMyModelAndView(modelAndView);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("operations/success", "accountDto", accountDto);
            buildMyModelAndView(modelAndView);
            modelAndView.addObject("message", "Nowy użytkownik zarejestrowany pomyślnie! :)");
            return modelAndView;
        }
    }

    @GetMapping("/successlogout")
    public String logout(Model model) {
        buildMyModel(model);
        model.addAttribute("accountDto", new UserDTO());
        model.addAttribute("logout", "success");
        return "login";
    }

    @GetMapping("/search")
    public String search(Model model, @RequestParam("c") Long category, @RequestParam("q") String query) {
        buildMyModel(model);
        List<Advertisement> advertisementsSearched = new ArrayList<>();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = new User();
        if (auth != null)
            user = userAndRoleService.findUserByEmail(auth.getName());

        model.addAttribute("user", user);

        for (Advertisement tmp : commonService.getSearchAdvertisementsByCriteria(category, query)) {
            if (tmp.isActive())
                advertisementsSearched.add(tmp);
        }
        Collections.sort(advertisementsSearched);
        if (query == null || query.isEmpty()) {
            return "redirect:/home";
        }
        model.addAttribute("advertisementsList", advertisementsSearched);
        if (category != null) {
            model.addAttribute("category", commonService.findCategoryById(category));
        }

        if (!query.replace(" ", "").isEmpty()) {
            model.addAttribute("query", query.replace(" ", ""));
        }

        Set<Category> advertisementsCategory = new HashSet<>();
        Set<University> advertisementsUniversities = new HashSet<>();
        Set<City> advertisementsCities = new HashSet<>();
        for (Advertisement advertisement : advertisementsSearched) {
            advertisementsCategory.add(advertisement.getCategory());
            advertisementsUniversities.add(advertisement.getUniversity());
            advertisementsCities.add(advertisement.getCity());
        }

        model.addAttribute("advertisementsCategory", advertisementsCategory);
        model.addAttribute("advertisementsUniversities", advertisementsUniversities);
        model.addAttribute("advertisementsCities", advertisementsCities);

        return "stack";
    }
}
