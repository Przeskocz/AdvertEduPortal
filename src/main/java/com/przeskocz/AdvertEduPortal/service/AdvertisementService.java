package com.przeskocz.AdvertEduPortal.service;

import com.przeskocz.AdvertEduPortal.DAO.AdvertisementDAO;
import com.przeskocz.AdvertEduPortal.model.Advertisement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class AdvertisementService {
    private final AdvertisementDAO advertisementDAO;

    @Autowired
    public AdvertisementService(AdvertisementDAO advertisementDAO) {
        this.advertisementDAO = advertisementDAO;

    }

    public Advertisement save(Advertisement a) {
        return advertisementDAO.save(a);
    }

    public Collection<Advertisement> getAllNewAdvertisements() {
        return advertisementDAO.findTop6ByExpirationDateGreaterThanEqualOrderByStartDateDesc(LocalDateTime.now())
                .orElse(new ArrayList<>());
    }

    public List<Advertisement> getAllAdvertisements() {
        List<Advertisement> advertisements = (List<Advertisement>) advertisementDAO.findAll();
        Collections.sort(advertisements);
        return advertisements;
    }

    public Collection<Advertisement> getAllActualAdvertisements() {
        List<Advertisement> actualAdvertisementList = new ArrayList<>();
        for (Advertisement tmp : getAllAdvertisements()) {
            if (tmp.isActive())
                actualAdvertisementList.add(tmp);
        }
        Collections.sort(actualAdvertisementList);
        return actualAdvertisementList;
    }

}
