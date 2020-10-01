package com.vinci.livraison.app.module.commande.service;

import com.vinci.livraison.app.module.client.entity.Client;
import com.vinci.livraison.app.module.commande.CreateCommandeForm;
import com.vinci.livraison.app.module.commande.entity.Commande;
import com.vinci.livraison.app.module.livreur.entity.Livreur;
import com.vinci.livraison.app.module.restaurateur.entity.Restaurateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICommandeRestaurateurService {

    Page<Commande> findCreatedCommandesByRestaurateur(Restaurateur restaurateur, Pageable pageable);

    Commande annulerCommande(Commande commande);

    Commande approverCommande(Commande commande);

    Page<Commande> findCommandesEnCoursDePreparationCommandesByRestaurateur(Restaurateur restaurateur, Pageable pageable);

    Page<Commande> findCommandesEnAttendeLivreurByRestaurateur(Restaurateur restaurateur, Pageable pageable);

    Commande affecterCommandeAuLiveur(Commande commande, Livreur livreur);

    Page<Commande> findCommandesEnAttendeLivraisonByRestaurateur(Restaurateur restaurateur, Pageable pageable);

    Page<Commande> findLivredCommandesByRestaurateur(Restaurateur restaurateur, Pageable pageable);

}
