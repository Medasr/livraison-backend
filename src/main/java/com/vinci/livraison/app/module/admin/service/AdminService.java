package com.vinci.livraison.app.module.admin.service;

import com.vinci.livraison.app.module.admin.CreateAdminForm;
import com.vinci.livraison.app.module.admin.entity.Admin;
import com.vinci.livraison.app.module.admin.repository.AdminRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdminService implements IAdminService {

    final private AdminRepository adminRepo;
    final private PasswordEncoder encoder;

    @Override
    public Admin create(CreateAdminForm form) {
        Admin admin = new Admin();
        admin.setDateCreation(LocalDateTime.now());
        admin.setPassword( encoder.encode( form.getPassword() ) );
        admin.setLogin(form.getLogin());
        return adminRepo.save(admin);
    }

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

        if (!encoder.matches(password,admin.getPassword())) {
            // TODO :: use custom Exception
            throw new RuntimeException("Password est incorrect");
        }

        admin.setPassword(encoder.encode(newPassword));
        return adminRepo.save(admin);
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