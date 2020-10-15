package com.vinci.livraison.app.module.admin.service;

import com.vinci.livraison.app.module.admin.entity.Admin;
import com.vinci.livraison.app.module.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService implements IAdminService {

    final private AdminRepository adminRepo;
    final private PasswordEncoder encoder;


    @Override
    public Admin updateLogin(Admin admin, String login) {
        if (adminRepo.existsByLogin(login)) {
            throw new RuntimeException("login deja existe");
        }
        admin.setLogin(login);
        return adminRepo.save(admin);
    }

    @Override
    public Admin updatePassword(Admin admin, String password, String newPassword) {

        if (!encoder.matches(password, admin.getPassword())) {
            // TODO :: use custom Exception
            throw new RuntimeException("Password est incorrect");
        }

        admin.setPassword(encoder.encode(newPassword));
        return adminRepo.save(admin);
    }

    @Override
    public Page<Admin> getAllAdmins(Pageable pageable){
        return adminRepo.findAll(pageable);
    }

    @Override
    public Optional<Admin> getAdminById(Long id) {
        return adminRepo.findById(id);
    }

    @Override
    public Optional<Admin> getAdminByLogin(String login) {
        return adminRepo.findByLogin(login);
    }
}
