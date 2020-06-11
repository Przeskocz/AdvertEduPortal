package com.przeskocz.AdvertEduPortal.service;

import com.przeskocz.AdvertEduPortal.DAO.RoleDAO;
import com.przeskocz.AdvertEduPortal.DAO.UserDAO;
import com.przeskocz.AdvertEduPortal.model.user.Role;
import com.przeskocz.AdvertEduPortal.model.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserAndRoleService {
    private final RoleDAO roleDAO;
    private final UserDAO userDAO;

    @Autowired
    public UserAndRoleService(RoleDAO roleDAO, UserDAO userDAO) {
        this.roleDAO = roleDAO;
        this.userDAO = userDAO;
    }

    public Role findOrCreateRole(Role role) {
        if (role == null)
            throw new NullPointerException("The role param cannot be null!");

        Role result = roleDAO.findById(role.getId()).orElse(null);
        if (result == null) {
            result = roleDAO.findByRole(role.getRole()).orElse(null);
        }
        if (result == null)
            result = roleDAO.save(role);

        return result;
    }

    public User findOrCreateUser(User user) {
        if (user == null)
            throw new NullPointerException("The role param cannot be null!");

        User result = userDAO.findById(user.getId()).orElse(null);
        if (result == null) {
            result = userDAO.findByNameOrEmail(user.getName(), user.getEmail()).orElse(null);
        }
        if (result == null)
            result = userDAO.save(user);

        return result;
    }

    public Role save(Role r) {
        return roleDAO.save(r);
    }

    public User save(User r) {
        return  userDAO.save(r);
    }

    public User findUserByEmail(String name) {
        return userDAO.findByName(name).orElse(null);
    }

    public Long countAllUsers() {
        return userDAO.count();
    }


}
