package com.vinci.livraison.app.module.commande.repository;

import com.vinci.livraison.app.module.client.entity.Client;
import com.vinci.livraison.app.module.commande.entity.Commande;
import com.vinci.livraison.app.module.livreur.entity.Livreur;
import com.vinci.livraison.app.module.restaurateur.entity.Restaurateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

@Repository
public interface CommandeRepository extends JpaRepository<Commande, Long> {

    @EntityGraph(attributePaths = {"restaurateur", "livreur", "client", "ligneCommandes.article"})
    @Override
    Optional<Commande> findById(Long id);

    // ------------ Restaurateur

    @EntityGraph(attributePaths = {"restaurateur", "livreur", "client", "ligneCommandes.article"})
    Optional<Commande> findByIdAndRestaurateur(long idCom, Restaurateur restaurateur);

    @EntityGraph(attributePaths = {"restaurateur", "livreur", "client"})
    Page<Commande> findAllByRestaurateur(Restaurateur restaurateur, Pageable pageable);

    @EntityGraph(attributePaths = {"restaurateur", "livreur", "client"})
    Page<Commande> findAllByRestaurateurAndEtat(Restaurateur restaurateur, Commande.Etat Etat, Pageable pageable);

    @EntityGraph(attributePaths = {"restaurateur", "livreur", "client"})
    Page<Commande> findAllByRestaurateurAndEtatIsIn(Restaurateur restaurateur, Collection<Commande.Etat> Etat, Pageable pageable);

    // ------------ Clients

    @EntityGraph(attributePaths = {"restaurateur", "livreur", "client", "ligneCommandes.article"})
    Optional<Commande> findByIdAndClientAndRestaurateurShutDownFalse(long idCom, Client client);

    @EntityGraph(attributePaths = {"restaurateur", "livreur", "client"})
    Page<Commande> findAllByRestaurateurShutDownFalseAndClient(Client client, Pageable pageable);

    @EntityGraph(attributePaths = {"restaurateur", "livreur", "client"})
    Page<Commande> findAllByRestaurateurShutDownFalseAndClientAndEtatIsIn(Client client, Collection<Commande.Etat> Etats, Pageable pageable);

    @EntityGraph(attributePaths = {"restaurateur", "livreur", "client"})
    Page<Commande> findAllByRestaurateurShutDownFalseAndClientAndEtat(Client client, Commande.Etat Etat, Pageable pageable);

    // ----------- Livreurs

    @EntityGraph(attributePaths = {"restaurateur", "livreur", "client"})
    Page<Commande> findAllByLivreurAndEtat(Livreur livreur, Commande.Etat Etats, Pageable pageable);


}
