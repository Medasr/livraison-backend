package com.vinci.livraison.app.module.livreur.repository;

import com.vinci.livraison.app.module.livreur.entity.Livreur;
import com.vinci.livraison.app.module.restaurateur.entity.Restaurateur;
import com.vinci.livraison.app.module.shared.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface LivreurRepository extends UserRepository<Livreur> {

    Page<Livreur> findAllByRestaurateur(Restaurateur restaurateur, Pageable pageable);

    Optional<Livreur> findByIdAndRestaurateur(long id, Restaurateur restaurateur);

    Page<Livreur> findAllByRestaurateurAndNomContainsIgnoreCase(Restaurateur restaurateur, String nom, Pageable pageable);

    // if not exists or his restaurateur Shutdown or enseigne not active
    boolean existsByRestaurateur_Enseigne_ActiveIsFalseOrRestaurateur_ShutDownIsTrue();

    long countAllByRestaurateurShutDownFalseAndRestaurateurEnseigneActiveTrue();

    long countAllByRestaurateur(Restaurateur restaurateur);

}
