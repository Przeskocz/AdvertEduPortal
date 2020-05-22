package com.przeskocz.AdvertEduPortal.model.convert;

import com.przeskocz.AdvertEduPortal.model.DAO.user.UserDAO;
import com.przeskocz.AdvertEduPortal.model.DTO.UserDTO;
import com.przeskocz.AdvertEduPortal.model.ModelConverter;
import org.springframework.stereotype.Component;


@SuppressWarnings("Duplicates")
@Component
public class UserConverter implements ModelConverter<UserDTO, UserDAO> {

    @Override
    public UserDAO convertToDAO(UserDTO dto) {
        UserDAO dao = new UserDAO();
        dao.setId(dto.getId());
        dao.setName(dto.getName());
        dao.setEmail(dto.getEmail());
        dao.setPassword(dto.getPassword());
        dao.setActive(dto.getActive());
        fillUserRole(dao);
        return dao;
    }

    @Override
    public UserDTO convertToDTO(UserDAO dao) {
        UserDTO dto = new UserDTO();
        dto.setId(dao.getId());
        dto.setName(dao.getName());
        dto.setEmail(dao.getEmail());
        dto.setPassword(dao.getPassword());
        dto.setActive(dao.getActive());
        return dto;
    }

    private void fillUserRole(UserDAO userDAO) {
        if (userDAO.getId() > 0L) {
            //TODO: wczytywanie uprawnień
        } else {
            //TODO: wczytywanie domyślnych uprawnień
        }
    }
}
