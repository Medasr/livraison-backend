package com.vinci.livraison.app.module.restaurateur.repository;


import com.vinci.livraison.app.module.restaurateur.entity.RestaurateurType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RestaurateurTypeRepository extends JpaRepository<RestaurateurType, Long> {

    boolean existsByTypeIdAndRestaurateurId(long id, Long id1);
}
