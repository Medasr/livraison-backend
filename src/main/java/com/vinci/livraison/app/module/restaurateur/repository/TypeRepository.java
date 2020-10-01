package com.vinci.livraison.app.module.restaurateur.repository;


import com.vinci.livraison.app.module.restaurateur.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TypeRepository extends JpaRepository<Type,Long> {

    List<Type> findAllByTitreContains(String titre);

    boolean existsByTitre(String titre);

}
