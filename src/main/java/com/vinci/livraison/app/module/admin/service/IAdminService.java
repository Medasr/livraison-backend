package com.vinci.livraison.app.module.admin.service;

import com.vinci.livraison.app.module.admin.CreateAdminForm;
import com.vinci.livraison.app.module.admin.entity.Admin;

import java.util.Optional;

public interface IAdminService {

    Admin updateLogin(Admin admin,String login);

    Admin updatePassword(Admin admin,String password,String newPassword);

    Optional<Admin> getAdminById(Long id);

    Optional<Admin> getAdminByLogin(String login);


}
