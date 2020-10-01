package com.vinci.livraison.app.module.enseigne.repository;

import com.vinci.livraison.app.module.enseigne.entity.Enseigne;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EnseigneRepository extends JpaRepository<Enseigne, Long> {


    boolean existsByIdAndActiveTrue(long id);

    boolean existsByEmailContact(String Email);

    long countAllByActive(boolean active);

    Optional<Enseigne> findByEmailContact(String email);

    Page<Enseigne> findAllByActive(boolean active, Pageable pageable);

    Page<Enseigne> findAllByActiveAndNomContains(boolean active, String nom, Pageable pageable);

    Page<Enseigne> findAllByNomContains(String nom, Pageable pageable);

}
