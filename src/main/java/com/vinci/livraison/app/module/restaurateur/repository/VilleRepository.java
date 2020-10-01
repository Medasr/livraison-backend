package com.vinci.livraison.app.module.restaurateur.repository;


import com.vinci.livraison.app.module.restaurateur.entity.Ville;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VilleRepository extends JpaRepository<Ville,Long> {

    boolean existsByNom(String nom);

    List<Ville> findAllByNomContains(String titre);

}
