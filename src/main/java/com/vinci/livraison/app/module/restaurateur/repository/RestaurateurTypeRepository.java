package com.vinci.livraison.app.module.restaurateur.repository;


import com.vinci.livraison.app.module.restaurateur.entity.Restaurateur;
import com.vinci.livraison.app.module.restaurateur.entity.RestaurateurType;
import com.vinci.livraison.app.module.restaurateur.entity.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurateurTypeRepository extends JpaRepository<RestaurateurType, Long> {

    boolean existsByTypeAndRestaurateur(Type type, Restaurateur restaurateur);

    boolean existsByType(Type type);
}
