package com.przeskocz.AdvertEduPortal.controller;

import com.przeskocz.AdvertEduPortal.model.Advertisement;
import com.przeskocz.AdvertEduPortal.model.DTO.AdvertisementDTO;
import com.przeskocz.AdvertEduPortal.model.User;
import com.przeskocz.AdvertEduPortal.service.AdvertisementService;
import com.przeskocz.AdvertEduPortal.service.UserAndRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/user")
public class UserController extends CommonController {
    private final AdvertisementService advertisementService;
    private final UserAndRoleService userAndRoleServiceService;

    @Autowired
    public UserController(AdvertisementService advertisementService, UserAndRoleService userAndRoleServiceService) {
        this.advertisementService = advertisementService;
        this.userAndRoleServiceService = userAndRoleServiceService;
    }

    @GetMapping("/advertisements")
    public String myAdvertisements(Model model) {
        buildMyModel(model);

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        if (auth != null)
            user = userAndRoleServiceService.findUserByEmail(auth.getName());

        if (user != null) {
            List<Advertisement> allAdvertisements = advertisementService.findAdvertisementsByUser(user);
            List<Advertisement> actualAdvertisements = allAdvertisements.stream()
                    .filter(Advertisement::isActive).collect(Collectors.toList());

            List<Advertisement> notActualAdvertisements = allAdvertisements.stream()
                    .filter(t -> !t.isActive()).collect(Collectors.toList());


            model.addAttribute("advertisementsActualList", actualAdvertisements);
            model.addAttribute("advertisementsNotActualList", notActualAdvertisements);
            model.addAttribute("query", "Moje ogłoszenia");
            return "mystack";
        }
        return "error/404";
    }

    @SuppressWarnings("Duplicates")
    @GetMapping("/advertisements/edit/{advertisement_id}")
    public String editAdvertisement(@PathVariable String advertisement_id, Model model) {
        buildMyModel(model);

        Advertisement advertisement = advertisementService.findAdvertisementById(stringToLong(advertisement_id));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = null;

        if (auth != null)
            user = userAndRoleServiceService.findUserByEmail(auth.getName());

        if (advertisement == null || user == null)
            return "error/404";
        if (!advertisement.getUser().equals(user))
            return "error/403";

        Map<Long, String> categoriesMap = commonService.getSearchOptions();
        categoriesMap.remove(1L);
        categoriesMap.remove(8L);
        categoriesMap.remove(9L);
        model.addAttribute("advertisement", advertisement.toDTO());
        model.addAttribute("categoriesList", categoriesMap);
        model.addAttribute("universitiesList", commonService.getAllUniversities());
        return "editAdvertisement";
    }

    @SuppressWarnings("Duplicates")
    @PostMapping("/advertisements/edit/{advertisement_id}")
    public ModelAndView editAdvertisement(@PathVariable String advertisement_id,
                                          @ModelAttribute("newAdvertisement") @Valid AdvertisementDTO advertisementDto,
                                          BindingResult result) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        if (auth != null)
            user = userAndRoleServiceService.findUserByEmail(auth.getName());

        if (!result.hasErrors() && user != null) {
            advertisementDto.setId(this.stringToLong(advertisement_id));
            advertisementService.updateAdvertisement(advertisementDto, user, result);
        }
        if (result.hasErrors()) {
            ModelAndView modelAndView = new ModelAndView("operations/failure", "advertisementDto", advertisementDto);
            modelAndView.addObject("message", "Nie udało się zapisać zmian w ogłoszeniu, spróbuj ponownie później :(");
            buildMyModelAndView(modelAndView);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("redirect:/user/advertisements?updateAds", "advertisementDto", advertisementDto);
            buildMyModelAndView(modelAndView);
            return modelAndView;
        }
    }

    @SuppressWarnings("Duplicates")
    @GetMapping("/advertisements/delete/{advertisement_id}")
    public ModelAndView deleteAdvertisement(@PathVariable String advertisement_id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        if (auth != null)
            user = userAndRoleServiceService.findUserByEmail(auth.getName());

        Error error = new Error();
        if (user != null) {
            advertisementService.deleteAdvertisement(this.stringToLong(advertisement_id), user, error);
        }
        if (error.getMessage() != null && !error.getMessage().isEmpty()) {
            ModelAndView modelAndView = new ModelAndView("operations/failure");
            modelAndView.addObject("message", "Uuuups coś poszło nie tak :(");
            buildMyModelAndView(modelAndView);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("redirect:/user/advertisements?deleteAds");
            buildMyModelAndView(modelAndView);
            return modelAndView;
        }
    }

    @GetMapping("/advertisements/image/delete/{id}")
    public ResponseEntity<HttpStatus> deleteImage(@PathVariable("id") long id) {
        Error error = null;
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        if (auth != null)
            user = userAndRoleServiceService.findUserByEmail(auth.getName());

        if (id > -1L && user != null) {
            advertisementService.deleteImageFromAdvertisement(id, user, error);
            if (error == null) {
                return new ResponseEntity<>(HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_MODIFIED);
    }

    @SuppressWarnings("Duplicates")
    @PostMapping("/advertisements/new")
    public ModelAndView registerNewAdvertisement(@ModelAttribute("newAdvertisement") @Valid AdvertisementDTO advertisementDto,
                                                 BindingResult result) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = null;
        if (auth != null)
            user = userAndRoleServiceService.findUserByEmail(auth.getName());

        if (!result.hasErrors() && user != null) {
            advertisementService.registerNewAdvertisement(advertisementDto, user);

            ModelAndView modelAndView = new ModelAndView("redirect:/user/advertisements?addNewAds", "advertisementDto", advertisementDto);
            buildMyModelAndView(modelAndView);
            return modelAndView;
        } else {
            ModelAndView modelAndView = new ModelAndView("operations/failure", "advertisementDto", advertisementDto);
            modelAndView.addObject("message", "Nie udało się dodać ogłoszenia, spróbuj ponownie później :(");
            buildMyModelAndView(modelAndView);
            return modelAndView;
        }
    }

    @SuppressWarnings("Duplicates")
    @GetMapping("/advertisements/new")
    public String newAdvertisementPage(Model model) {
        buildMyModel(model);
        model.addAttribute("newAdvertisement", new AdvertisementDTO());
        Map<Long, String> categoriesMap = commonService.getSearchOptions();
        categoriesMap.remove(1L);
        categoriesMap.remove(8L);
        categoriesMap.remove(9L);
        model.addAttribute("categoriesList", categoriesMap);
        model.addAttribute("universitiesList", commonService.getAllUniversities());
        return "newAdvertisement";
    }

    private long stringToLong(String val) {
        long idd = -1L;
        try {
            idd = Long.parseLong(val);
        } catch (NumberFormatException e) {
            System.out.println("AdvertisementController.getAdvertisementDetails()@GetMapping > NumberFormatException " + e.getMessage());
        }
        return idd;
    }
}
