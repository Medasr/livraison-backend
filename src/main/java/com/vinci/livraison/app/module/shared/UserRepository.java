package com.vinci.livraison.app.module.shared;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.Optional;

@NoRepositoryBean
public interface UserRepository<T extends User> extends JpaRepository<T,Long> {

    Optional<T> findByLogin(String Login);

    boolean existsByLogin(String Login);

}
