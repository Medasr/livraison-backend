package com.vinci.livraison.app.module.client.repository;

import com.vinci.livraison.app.module.client.entity.AdresseLivraison;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AdresseLivraisonRepository extends JpaRepository<AdresseLivraison,Long> {

    boolean existsByClientIdAndPrimaryTrue(long idClient);

    Optional<AdresseLivraison> findByClientIdAndPrimaryTrue(Long id);

    List<AdresseLivraison> findAllByClientId(Long id);
}
