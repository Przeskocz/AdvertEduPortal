package com.przeskocz.AdvertEduPortal.controller;

import com.fasterxml.jackson.annotation.JacksonAnnotationsInside;
import com.przeskocz.AdvertEduPortal.model.Advertisement;
import com.przeskocz.AdvertEduPortal.model.Category;
import com.przeskocz.AdvertEduPortal.model.City;
import com.przeskocz.AdvertEduPortal.model.University;
import com.przeskocz.AdvertEduPortal.service.AdvertisementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.*;

@Controller
@RequestMapping("/advertisement")
public class AdvertController extends CommonController {

    private final AdvertisementService advertisementService;

    @Autowired
    public AdvertController(AdvertisementService advertisementService) {
        this.advertisementService = advertisementService;
    }

    @SuppressWarnings("Duplicates")
    @GetMapping("/{advertisement_id}")
    public String getAdvertisementDetails(@PathVariable String advertisement_id, Model model) {
        buildMyModel(model);
        long idd = -1;
        try {
            idd = Long.parseLong(advertisement_id);
        } catch (NumberFormatException e) {
            System.out.println("AdvertisementController.getAdvertisementDetails()@GetMapping > NumberFormatException " + e.getMessage());
            return "redirect:/404";
        }
        Advertisement searchAdvertisement = advertisementService.findAdvertisementById(idd);
        if (searchAdvertisement == null)
            return "redirect:/404";

        List<Advertisement> otherAdvertisements = advertisementService.getAllActualAdvertisementsByUser(searchAdvertisement.getUser());
        otherAdvertisements.remove(searchAdvertisement);

        Collections.sort(otherAdvertisements);
        if(otherAdvertisements.size()>4) {
            List<Advertisement> newList = new ArrayList<>();
            for(int i=0; i<4; i++) {
                newList.add(otherAdvertisements.get(i));
            }
            otherAdvertisements = newList;
        }

        model.addAttribute("advertisement", searchAdvertisement);
        model.addAttribute("otherAdvertisements", otherAdvertisements);
        return "advertisement";
    }



    @GetMapping("/university/{university_name}")
    public String getAdvertisementsByUniversity(@PathVariable String university_name, Model model) {
        buildMyModel(model);

        model.addAttribute("advertisementsList", advertisementService.findAdvertisementsByUniversityName(university_name));
        model.addAttribute("category", commonService.findCategoryById(9L));
        model.addAttribute("query", university_name);
        return getAdvertisementList(model);
    }


    @GetMapping("/list")
    public String getAdvertisementList(Model model) {
        buildMyModel(model);
        if(!model.containsAttribute("advertisementsList")) {
            model.addAttribute("advertisementsList", advertisementService.getAllActualAdvertisements());
        }

        if(!model.containsAttribute("category")) {
            model.addAttribute("category", commonService.findCategoryById(1L));
        }

        if(model.asMap().get("advertisementsList") instanceof List) {
            Set<Category> advertisementsCategory = new HashSet<>();
            Set<University> advertisementsUniversities = new HashSet<>();
            Set<City> advertisementsCities = new HashSet<>();
            for (Object object : (List) model.asMap().get("advertisementsList")) {
                if(object instanceof Advertisement) {
                    advertisementsCategory.add(((Advertisement)object).getCategory());
                    advertisementsUniversities.add(((Advertisement)object).getUniversity());
                    advertisementsCities.add(((Advertisement)object).getCity());
                }
            }

            model.addAttribute("advertisementsCategory", advertisementsCategory);
            model.addAttribute("advertisementsUniversities", advertisementsUniversities);
            model.addAttribute("advertisementsCities", advertisementsCities);
        }

        return "stack";
    }


    @GetMapping("/list/{type}")
    public String getAdvertisementListByType(Model model, @PathVariable String type) {
        buildMyModel(model);
        switch(type.toUpperCase()){
            case "SELL" :
                model.addAttribute("advertisementsList", advertisementService.getAllAdvertisementToSell());
                model.addAttribute("query", "Sprzedam/Oddam/Wykonam");
                break;
            case "BUY":
                model.addAttribute("advertisementsList", advertisementService.getAllAdvertisementToBuy());
                model.addAttribute("query", "Kupię/Zlecę");
                break;
            case "TUTORING":
                model.addAttribute("advertisementsList", advertisementService.getAllAdvertisementToTutoring());
                model.addAttribute("query", "Korepetycje");
                break;
        }

        return getAdvertisementList(model);
    }
}
