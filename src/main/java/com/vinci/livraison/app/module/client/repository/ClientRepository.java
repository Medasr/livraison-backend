package com.vinci.livraison.app.module.client.repository;

import com.vinci.livraison.app.module.client.entity.Client;
import com.vinci.livraison.app.module.shared.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends UserRepository<Client> {

    Page<Client> findAllByNomIsContaining(String nom, Pageable pageable);


}
