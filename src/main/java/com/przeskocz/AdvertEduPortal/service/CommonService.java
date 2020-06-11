package com.przeskocz.AdvertEduPortal.service;

import com.przeskocz.AdvertEduPortal.DAO.AdvertisementDAO;
import com.przeskocz.AdvertEduPortal.DAO.CategoryDAO;
import com.przeskocz.AdvertEduPortal.DAO.CityDAO;
import com.przeskocz.AdvertEduPortal.DAO.UniversityDAO;
import com.przeskocz.AdvertEduPortal.model.Advertisement;
import com.przeskocz.AdvertEduPortal.model.Category;
import com.przeskocz.AdvertEduPortal.model.Category.CategoryEnum;
import com.przeskocz.AdvertEduPortal.model.City;
import com.przeskocz.AdvertEduPortal.model.University;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class CommonService {
    private final CategoryDAO categoryDAO;
    private final CityDAO cityDAO;
    private final UniversityDAO universityDAO;
    private final AdvertisementDAO advertisementDAO;

    @Autowired
    public CommonService(CategoryDAO categoryDAO, CityDAO cityDAO, UniversityDAO universityDAO, AdvertisementDAO advertisementDAO) {
        this.categoryDAO = categoryDAO;
        this.cityDAO = cityDAO;
        this.universityDAO = universityDAO;
        this.advertisementDAO = advertisementDAO;
    }


    public Map<Long, String> getSearchOptions() {
        List<Category> categories = (List<Category>) categoryDAO.findAll();

        Map<Long, String> sarchOptions = new TreeMap<>();
        for (Category tmp : categories) {
            sarchOptions.put(tmp.getId(), tmp.getName());
        }

        return sarchOptions;
    }

    
    public List<Category> getAllAdvertisementCategories(){
        List<Category> categories = (List<Category>) categoryDAO.findAll();
        categories.remove(categoryDAO.findById(CategoryEnum.DEFAULT.getValue()).get());
        categories.remove(categoryDAO.findById(CategoryEnum.MIASTO.getValue()).get());
        categories.remove(categoryDAO.findById(CategoryEnum.UCZELNIA.getValue()).get());

        return categories;
    }

    
    public List<City> getAllCities() {
        return (List<City>) cityDAO.findAll();
    }

    
    public List<University> getAllUniversities() {
        List<University> result = (List<University>) universityDAO.findAll();
        Collections.sort(result);
        return result;
    }

    
    public List<University> getAllUniversitiesByCityName(String cityName) {
        return universityDAO.findAllByCity_NameIgnoreCaseContaining(cityName);
    }

    
    public List<University> getAllUniversitiesByCity(City city) {
        return getAllUniversitiesByCityName(city.getName());
    }

    
    public Category findCategoryById(Long id) {
        return categoryDAO.findById(id).orElse(null);
    }

    
    public Category findCategoryByName(String name) {
        return categoryDAO.findByNameContainingIgnoreCase(name).orElse(null);
    }

    
    public Long countAllUniversities() {
        return universityDAO.count();
    }

    
    public Long countAllCategories() {
        return categoryDAO.count();
    }

    
    public List<Advertisement> getSearchAdvertisementsByCriteria(Long categoryId, String query) {
        List<Advertisement> searchObjects = null;
        Set<Advertisement> setAdvertisements = new HashSet<>();
        CategoryEnum categoryEnum = CategoryEnum.DEFAULT;

        if (query == null || query.replace(" ", "").isEmpty()) {
            query = "";
        }
        query = query.replace(" ", "%");

        if (categoryId != null) {
            categoryEnum = categoryEnum.getEnum(categoryId);
        }

        switch (categoryEnum) {
            case SPRZEDAM:
            case KUPIE:
            case ODDAM:
            case WYKONAM:
            case ZLECE:
            case KOREPETYCJE:
                searchObjects = advertisementDAO.findAllByCategory_IdAndTitleIgnoreCaseContainingAndExpirationDateGreaterThanEqualOrderByStartDateDesc(categoryEnum.getValue(), query, LocalDateTime.now());
                Collections.sort(searchObjects);
                break;
            case MIASTO:
                //setAdvertisements.addAll(advertisementDAO.findAllByCity_CityNameIgnoreCaseContainingAndExpirationDateGreaterThanEqualOrderByStartDateDesc(query, new Date()));
                for (Advertisement tmpAd : advertisementDAO.findAllByCity_NameIgnoreCaseContaining(query)) {
                    if(tmpAd.isActive())
                        setAdvertisements.add(tmpAd);
                }
                setAdvertisements.addAll(getAdvertisementsByUniversityCity(query));
                searchObjects = new ArrayList<>(setAdvertisements);
                Collections.sort(searchObjects);
                break;
            case UCZELNIA:
                searchObjects = advertisementDAO.findAllByUniversity_NameIgnoreCaseContainingOrUniversity_ShortNameIgnoreCaseContainingAndExpirationDateGreaterThanEqualOrderByStartDateDesc(query, query, LocalDateTime.now());
                Collections.sort(searchObjects);
                break;
            case DEFAULT:
                //setAdvertisements.addAll(advertisementDAO.findAllByCity_CityNameIgnoreCaseContainingAndExpirationDateGreaterThanEqualOrderByStartDateDesc(query, new Date()));
                for (Advertisement tmpAd : advertisementDAO.findAllByCity_NameIgnoreCaseContaining(query)) {
                    if(tmpAd.isActive())
                        setAdvertisements.add(tmpAd);
                }
                setAdvertisements.addAll(getAdvertisementsByUniversityCity(query));
                setAdvertisements.addAll(advertisementDAO.findAllByTitleIgnoreCaseContainingAndExpirationDateGreaterThanEqualOrderByStartDateDesc(query, LocalDateTime.now()));
                setAdvertisements.addAll(advertisementDAO.findAllByUniversity_NameIgnoreCaseContainingOrUniversity_ShortNameIgnoreCaseContainingAndExpirationDateGreaterThanEqualOrderByStartDateDesc(query, query,  LocalDateTime.now()));

                searchObjects = new ArrayList<>(setAdvertisements);
                Collections.sort(searchObjects);
                break;
        }

        return searchObjects;
    }

    private Set<Advertisement> getAdvertisementsByUniversityCity(String city) {
        Set<Advertisement> advertisements = new HashSet<>();
        for (University university : getAllUniversitiesByCityName(city)) {
                advertisements.addAll(advertisementDAO.findAllByUniversity_NameIgnoreCaseContainingOrUniversity_ShortNameIgnoreCaseContainingAndExpirationDateGreaterThanEqualOrderByStartDateDesc(university.getName(), university.getShortName(), LocalDateTime.now()));
        }
        return advertisements;
    }
}
