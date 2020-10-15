package com.vinci.livraison.app.module.admin.service;

import com.vinci.livraison.app.module.admin.entity.Admin;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IAdminService {

    Admin updateLogin(Admin admin, String login);

    Admin updatePassword(Admin admin, String password, String newPassword);

    Page<Admin> getAllAdmins(Pageable pageable);

    Optional<Admin> getAdminById(Long id);

    Optional<Admin> getAdminByLogin(String login);


}
