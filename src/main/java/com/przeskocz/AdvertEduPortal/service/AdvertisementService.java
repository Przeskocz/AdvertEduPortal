package com.przeskocz.AdvertEduPortal.service;

import com.przeskocz.AdvertEduPortal.DAO.AdvertisementDAO;
import com.przeskocz.AdvertEduPortal.DAO.CategoryDAO;
import com.przeskocz.AdvertEduPortal.DAO.ImageDAO;
import com.przeskocz.AdvertEduPortal.DAO.UniversityDAO;
import com.przeskocz.AdvertEduPortal.model.Advertisement;
import com.przeskocz.AdvertEduPortal.model.DTO.AdvertisementDTO;
import com.przeskocz.AdvertEduPortal.model.Image;
import com.przeskocz.AdvertEduPortal.model.University;
import com.przeskocz.AdvertEduPortal.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

@Service
public class AdvertisementService {
    private final AdvertisementDAO advertisementDAO;
    private final ImageDAO imageDAO;
    private final CategoryDAO categoryDAO;
    private final UniversityDAO universityDAO;

    private final String RELATIVE_PATH_IMAGE = "/img/files";

    @Autowired
    public AdvertisementService(AdvertisementDAO advertisementDAO, ImageDAO imageDAO, CategoryDAO categoryDAO, UniversityDAO universityDAO) {
        this.advertisementDAO = advertisementDAO;

        this.imageDAO = imageDAO;
        this.categoryDAO = categoryDAO;
        this.universityDAO = universityDAO;
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

    public List<Advertisement> findAdvertisementsByUser(User user) {
        return advertisementDAO.findAllByUser(user);
    }

    public Advertisement findAdvertisementById(Long id) {
        return advertisementDAO.findById(id).orElse(null);
    }

    public void updateAdvertisement(AdvertisementDTO advertisementDto, User user, BindingResult result) {
        boolean isModified = false;
        if (advertisementDto.getId() == null || advertisementDto.getId() < 0L) {
            result.rejectValue("advertisement", "Advertisement not found.");
            return;
        }
        Optional<Advertisement> optionalAdvertisement = advertisementDAO.findById(advertisementDto.getId());
        if (!optionalAdvertisement.isPresent()) {
            result.rejectValue("advertisement", "Advertisement not found.");
            return;
        }
        Advertisement advertisement = optionalAdvertisement.get();
        if (!advertisement.getUser().equals(user)) {
            result.rejectValue("advertisement", "The user is not the owner of this advertisement.");
            return;
        }

        if (!advertisement.getTitle().equals(advertisementDto.getTitle())) {
            isModified = true;
            advertisement.setTitle(advertisementDto.getTitle());
        }
        if (!advertisement.getDescription().equals(advertisementDto.getDescription())) {
            isModified = true;
            advertisement.setDescription(advertisementDto.getDescription());
        }
        if (advertisement.getPrice().compareTo(advertisementDto.getPrice()) != 0) {
            isModified = true;
            advertisement.setPrice(advertisementDto.getPrice());
        }

        LocalDateTime expirationOfDTO = advertisementDto.getExpirationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        if (advertisement.getExpirationDate().compareTo(expirationOfDTO) != 0) {
            isModified = true;
            advertisement.setExpirationDate(expirationOfDTO);
        }

        saveImagesForAdvertisement(bringOutNewImagesFromAdvertisementDto(advertisementDto), advertisement);

        if (isModified)
            advertisementDAO.save(advertisement);
    }

    private void saveImagesForAdvertisement(List<Image> imagesToSave, Advertisement advertisement) {
        for (Image imgToSave : imagesToSave) {
            imgToSave.setAdvertisement(advertisement);
            imgToSave.setTimestamp(new Timestamp(System.currentTimeMillis()));
        }

        if (!imagesToSave.isEmpty())
            imageDAO.saveAll(imagesToSave);
    }

    private List<Image> bringOutNewImagesFromAdvertisementDto(AdvertisementDTO dto) {
        List<Image> listOfImages = new ArrayList<>();
        if (dto != null) {
            for (MultipartFile file : dto.getImages()) {
                Image image = bringOutImageFromMultipartFile(file);
                if (image != null) {
                    image.setAlt(dto.getTitle());
                    listOfImages.add(image);
                }
            }
        }
        return listOfImages;
    }

    private Image bringOutImageFromMultipartFile(MultipartFile file) {
        Image image = null;
        String path;
        if (!file.isEmpty()) {
            String extension = null;
            String filename = file.getOriginalFilename();

            Optional<String> optionalExt = this.getExtensionByStringHandling(filename);
            if (optionalExt.isPresent())
                extension = optionalExt.get();

            if (extension != null) {
                try {
                    UUID uuid = UUID.randomUUID();
                    filename = "/upload_" + uuid.toString() + (extension.isEmpty() ? "" : "." + extension);
                    byte[] bytes = file.getBytes();
                    path = this.prepareFileDirectory() + filename;
                    File fsFile = new File(path);
                    fsFile.createNewFile();
                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(fsFile));
                    stream.write(bytes);
                    stream.close();
                } catch (Exception e) {
                    System.out.println("File has not been uploaded:\n" + e);
                }
                image = new Image();
                image.setPath(RELATIVE_PATH_IMAGE + filename);
            }
        } else {
            System.out.println("Uploaded file is empty");
        }

        return image;
    }

    private Optional<String> getExtensionByStringHandling(String filename) {
        return Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1));
    }

    private String prepareFileDirectory() {
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource(".");

        String sPath = url != null ? url.getPath() : "";
        sPath = (sPath + "static" + RELATIVE_PATH_IMAGE)
                .substring(1)
                .replace("%c5%82", "ł");
        Path path = Paths.get(sPath);
        if (!Files.exists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return path.toString();
    }

    public void deleteAdvertisement(Long id, User user, Error error) {
        if (id == null || id < 0L) {
            error = new Error("Advertisement not found.");
            return;
        }
        Optional<Advertisement> optionalAdvertisement = advertisementDAO.findById(id);
        if (!optionalAdvertisement.isPresent()) {
            error = new Error("Advertisement not found.");
            return;
        }
        Advertisement advertisement = optionalAdvertisement.get();
        if (!advertisement.getUser().equals(user)) {
            error = new Error("The user is not the owner of this advertisement.");
            return;
        }

        List<Image> imgToDelete = advertisement.getImages();
        imageDAO.deleteAll(imgToDelete);
        deleteAllImagesFromResources(imgToDelete);
        advertisementDAO.delete(advertisement);
    }

    private void deleteAllImagesFromResources(List<Image> listOfImage) {
        for (Image img : listOfImage) {
            if (deleteImageFromResources(img))
                System.out.println("Deleted the " + img.getPath() + " image");
            else
                System.out.println("Not deleted the " + img.getPath() + " image");
        }
    }

    private boolean deleteImageFromResources(Image img) {
        File fsFile = new File(getStaticPathDirectory() + img.getPath());
        return fsFile.delete();
    }

    private String getStaticPathDirectory() {
        ClassLoader classLoader = getClass().getClassLoader();
        URL url = classLoader.getResource(".");
        String spath = url != null ? url.getPath() : "";
        Path path = Paths.get((spath + "static").substring(1).replace("%c5%82", "ł"));
        return path.toString();
    }


    public void deleteImageFromAdvertisement(Long id, User user, Error error) {
        if (id > -1L) {
            Image img = imageDAO.findById(id).orElse(null);

            if (img != null && img.getAdvertisement() != null) {
                if (!img.getAdvertisement().getUser().equals(user)) {
                    error = new Error("The user is not the owner of this advertisement.");
                    return;
                }
                imageDAO.delete(img);
                if (!deleteImageFromResources(img)) {
                    error = new Error("Cannot delete the " + img.getPath() + " image from storage.");
                }
            } else {
                error = new Error("The image not found.");
            }
        } else {
            error = new Error("The image ID is invalid.");
        }
    }


    public Advertisement registerNewAdvertisement(AdvertisementDTO advertisementDto, User user) {
        Advertisement advertisement = new Advertisement();
        advertisement.setTitle(advertisementDto.getTitle());
        advertisement.setDescription(advertisementDto.getDescription());
        advertisement.setPrice(advertisementDto.getPrice());
        advertisement.setCategory(categoryDAO.findById(advertisementDto.getCategoryId()).orElse(null));

        University univ = universityDAO.findById(advertisementDto.getUniversityId()).orElse(null);
        advertisement.setUniversity(univ);
        advertisement.setCity(univ == null ? null : univ.getCity());

        advertisement.setUser(user);
        advertisement.setStartDate(LocalDateTime.now());

        List<Image> imagesToSave = bringOutNewImagesFromAdvertisementDto(advertisementDto);

        LocalDateTime expirationOfDTO = advertisementDto.getExpirationDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        advertisement.setExpirationDate(expirationOfDTO);
        advertisement = advertisementDAO.save(advertisement);

        saveImagesForAdvertisement(imagesToSave, advertisement);

        return advertisement;
    }

    public List<Advertisement> getAllActualAdvertisementsByUser(User user) {
        List<Advertisement> actualAdvertisementList = new ArrayList<>();
        for (Advertisement tmp : getAllActualAdvertisements()) {
            if (tmp.getUser().equals(user)) {
                actualAdvertisementList.add(tmp);
            }
        }
        Collections.sort(actualAdvertisementList);
        return actualAdvertisementList;
    }

    public List<Advertisement> findAdvertisementsByUniversityName(String universityName) {
        List<Advertisement> advertisements = new ArrayList<>();
        for(Advertisement advertisement : advertisementDAO.findAll()) {
            if(advertisement.getUniversity().getName().equalsIgnoreCase(universityName)
                    || advertisement.getUniversity().getShortName().equalsIgnoreCase(universityName) && advertisement.isActive()) {
                advertisements.add(advertisement);
            }
        }
        Collections.sort(advertisements);
        return advertisements;
    }

    public List<Advertisement> getAllAdvertisementToSell() {
        List<Advertisement> advertisements = new ArrayList<>();
        for(Advertisement tmp : getAllAdvertisements()) {
            if(tmp.isActive()) {
                Long catId = tmp.getCategory().getId();
                if (catId.equals(2L) || catId.equals(4L) || catId.equals(5L)) {
                    advertisements.add(tmp);
                }
            }
        }
        Collections.sort(advertisements);
        return advertisements;
    }

    public List<Advertisement> getAllAdvertisementToBuy() {
        List<Advertisement> advertisements = new ArrayList<>();
        for(Advertisement tmp : getAllAdvertisements()) {
            if(tmp.isActive()) {
                Long catId = tmp.getCategory().getId();
                if (catId.equals(3L) || catId.equals(6L)) {
                    advertisements.add(tmp);
                }
            }
        }
        Collections.sort(advertisements);
        return advertisements;
    }

    public List<Advertisement> getAllAdvertisementToTutoring() {
        List<Advertisement> advertisements = new ArrayList<>();
        for (Advertisement tmp : getAllAdvertisements()) {
            if (tmp.isActive()) {
                Long catId = tmp.getCategory().getId();
                if (catId.equals(7L)) {
                    advertisements.add(tmp);
                }
            }
        }
        Collections.sort(advertisements);
        return advertisements;
    }
}
