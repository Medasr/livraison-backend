package com.vinci.livraison.app.module.commande.service;

import com.vinci.livraison.app.module.commande.entity.Commande;
import com.vinci.livraison.app.module.livreur.entity.Livreur;
import com.vinci.livraison.app.module.restaurateur.entity.Restaurateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ICommandeRestaurateurService {

    Optional<Commande> findCommandeByIdAndRestaurateur(long id, Restaurateur restaurateur);

    Page<Commande> findCreatedCommandesByRestaurateur(Restaurateur restaurateur, Pageable pageable);

    Commande annulerCommandeByRestaurateur(Commande commande);

    Commande approverCommande(Commande commande);

    Page<Commande> findCommandesEnCoursDePreparationCommandesByRestaurateur(Restaurateur restaurateur, Pageable pageable);

    Commande commandePrete(Commande commande);

    Page<Commande> findCommandesEnAttendeLivreurByRestaurateur(Restaurateur restaurateur, Pageable pageable);

    Commande affecterCommandeAuLiveur(Commande commande, Livreur livreur);

    Page<Commande> findCommandesEnAttendeLivraisonByRestaurateur(Restaurateur restaurateur, Pageable pageable);

    Page<Commande> findLivredCommandesByRestaurateur(Restaurateur restaurateur, Pageable pageable);

}
