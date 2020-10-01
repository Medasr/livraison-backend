package com.vinci.livraison.app.module.restaurateur.repository;

import com.vinci.livraison.app.module.restaurateur.entity.RestaurateurUser;
import com.vinci.livraison.app.module.shared.UserRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurateurUserRepository extends UserRepository<RestaurateurUser> {

}
