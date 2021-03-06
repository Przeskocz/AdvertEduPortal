package com.przeskocz.AdvertEduPortal.DAO;

import com.przeskocz.AdvertEduPortal.model.Role;
import com.przeskocz.AdvertEduPortal.model.UserRoleEnum;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleDAO extends CrudRepository<Role, Long> {
    Optional<Role> findByRole(UserRoleEnum role);
}
