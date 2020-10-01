package com.vinci.livraison.app.module.restaurateur.repository;


import com.vinci.livraison.app.module.restaurateur.entity.RestaurateurType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurateurTypeRepository extends JpaRepository<RestaurateurType, Long> {

    boolean existsByTypeIdAndRestaurateurId(long id, Long id1);
}
