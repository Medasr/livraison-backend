package com.vinci.livraison.app.module.livreur.service;

import com.vinci.livraison.app.module.livreur.CreateLivreurForm;
import com.vinci.livraison.app.module.livreur.entity.Livreur;
import com.vinci.livraison.app.module.restaurateur.entity.Restaurateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ILivreurService {

    Optional<Livreur> findLivreurById(long id);

    Optional<Livreur> findLivreurByIdAndRestaurateur(long id,Restaurateur restaurateur);

    Page<Livreur> findLivreursByRestaurateur(Restaurateur restaurateur, Pageable pageable);

    Livreur createLivreur(Restaurateur restaurateur, CreateLivreurForm form);

    Livreur updateNumTel(Livreur livreur,String tel);

    Livreur updatePassword(Livreur livreur,String password ,String newPassword );


}
