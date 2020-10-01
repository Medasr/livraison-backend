package com.vinci.livraison.app.module.admin.repository;

import com.vinci.livraison.app.module.admin.entity.Admin;
import com.vinci.livraison.app.module.shared.UserRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface AdminRepository extends UserRepository<Admin> {

}
