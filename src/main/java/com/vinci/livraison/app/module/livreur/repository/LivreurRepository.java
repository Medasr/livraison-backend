package com.vinci.livraison.app.module.livreur.repository;

import com.vinci.livraison.app.module.livreur.entity.Livreur;
import com.vinci.livraison.app.module.shared.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface LivreurRepository extends UserRepository<Livreur> {

    Page<Livreur> findAllByRestaurateurId(long idRestaurateur, Pageable pageable);

    Optional<Livreur> findByIdAndRestaurateurId(long id,long idRestaurateur);

    Page<Livreur> findAllByRestaurateurIdAndNomContainsIgnoreCase(long idRestaurateur,String nom, Pageable pageable);

    // if not exists or his restaurateur Shutdown or enseigne not active
    boolean existsByRestaurateur_Enseigne_ActiveIsFalseOrRestaurateur_ShutDownIsTrue();

    long countAllByRestaurateurShutDownFalseAndRestaurateurEnseigneActiveTrue();

    long countAllByRestaurateurId(long idRest);

}
