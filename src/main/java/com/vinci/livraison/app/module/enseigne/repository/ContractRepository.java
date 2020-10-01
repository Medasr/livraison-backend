package com.vinci.livraison.app.module.enseigne.repository;

import com.vinci.livraison.app.module.enseigne.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContractRepository extends JpaRepository<Contract,Long> {

}
