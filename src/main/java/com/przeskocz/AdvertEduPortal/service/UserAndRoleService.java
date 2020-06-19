package com.przeskocz.AdvertEduPortal.service;

import com.przeskocz.AdvertEduPortal.DAO.RoleDAO;
import com.przeskocz.AdvertEduPortal.DAO.UserDAO;
import com.przeskocz.AdvertEduPortal.model.DTO.UserDTO;
import com.przeskocz.AdvertEduPortal.model.Role;
import com.przeskocz.AdvertEduPortal.model.User;
import com.przeskocz.AdvertEduPortal.model.UserRoleEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserAndRoleService {
    private final RoleDAO roleDAO;
    private final UserDAO userDAO;

    private final BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserAndRoleService(RoleDAO roleDAO, UserDAO userDAO, BCryptPasswordEncoder passwordEncoder) {
        this.roleDAO = roleDAO;
        this.userDAO = userDAO;
        this.passwordEncoder = passwordEncoder;
    }

    public Role findOrCreateRole(UserRoleEnum roleEnum) {
        if (roleEnum == null)
            throw new NullPointerException("The role param cannot be null!");

        Role result = roleDAO.findByRole(roleEnum).orElse(null);
        if (result == null)
            result = roleDAO.save(new Role (-1L, roleEnum));

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

    public User findUserByEmail(String email) {
        return userDAO.findByEmail(email).orElse(userDAO.findByName(email).orElse(null));
    }

    public Long countAllUsers() {
        return userDAO.count();
    }

    @Transactional
    public User registerNewUserAccount(UserDTO accountDto, BindingResult result) {
        if (emailExists(accountDto.getEmail())) {
            result.rejectValue("emailExists", "Istnieje już konto z adresem email " + accountDto.getEmail() + " :(");
        }

        if (!passwordsMatch(accountDto.getPassword(), accountDto.getMatchingPassword())) {
            result.rejectValue("passwordMatching", "Podane hasła nie pasują do siebie lub nie spełniają wymagań!");
        }

        User user = new User();
        user.setName(accountDto.getName());
        user.setEmail(accountDto.getEmail());
        user.setPassword(passwordEncoder.encode(accountDto.getPassword()));
        user.setActive(1);

        this.addRole(user, UserRoleEnum.USER);
        //this.addRole(user, UserRoleEnum.ADMIN);

        return userDAO.save(user);
    }

    private boolean emailExists(String email) {
        return this.findUserByEmail(email) != null;
    }
    private boolean passwordsMatch(String p1, String p2) {
        return p1.equals(p2) && p1.length()>=6;
    }

    private void addRole(User user, UserRoleEnum roleEnum) {
        Role newRole = findOrCreateRole(roleEnum);
        if (newRole != null)
            user.addRole(newRole);
    }

    private void deleteRole(User user, UserRoleEnum roleEnum) {
        Role toRemove = roleDAO.findByRole(roleEnum).orElse(null);
        if (toRemove != null)
            user.getRoles().remove(toRemove);
    }
}
