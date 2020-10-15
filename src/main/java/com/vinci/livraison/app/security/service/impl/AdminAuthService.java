package com.vinci.livraison.app.security.service.impl;

import com.vinci.livraison.app.module.admin.entity.Admin;
import com.vinci.livraison.app.module.admin.repository.AdminRepository;
import com.vinci.livraison.app.security.exceptions.UserNotFoundException;
import com.vinci.livraison.app.security.service.AuthServiceInterface;
import com.vinci.livraison.app.security.userdetails.impl.AdminDetails;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@Transactional
@AllArgsConstructor
public class AdminAuthService implements AuthServiceInterface<AdminDetails, Admin> {

    AdminRepository admin$;
    PasswordEncoder encoder;

    @Override
    public AdminDetails loadUserById(Long id) throws UserNotFoundException {
        return admin$.findById(id)
                .map(this::map)
                .orElseThrow(() -> new UserNotFoundException("User non trouvé"));
    }

    @Override
    public AdminDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return admin$.findByLogin(s)
                .map(this::map)
                .orElseThrow(() -> new UsernameNotFoundException("User non trouver"));
    }

    @Override
    public boolean isPasswordValid(AdminDetails user, String password) {
        return encoder.matches(password, user.getPassword());
    }

    @Override
    public AdminDetails map(Admin admin) {
        Set<String> authorities = new HashSet<>();
        authorities.add("ROLE_ADMIN");

        return new AdminDetails(
                admin.getId(),
                "Admin",
                admin.getLogin(),
                admin.getPassword(),
                authorities,
                true
        );
    }
}
