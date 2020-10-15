package com.vinci.livraison.app.module.commande.service.impl;


import com.vinci.livraison.app.module.article.repository.ArticleRepository;
import com.vinci.livraison.app.module.client.entity.Client;
import com.vinci.livraison.app.module.commande.CreateClientCommandeForm;
import com.vinci.livraison.app.module.commande.CreateCommandeForm;
import com.vinci.livraison.app.module.commande.entity.Commande;
import com.vinci.livraison.app.module.commande.entity.LigneCommande;
import com.vinci.livraison.app.module.commande.exception.CommandeCantHaveNoLigneCommande;
import com.vinci.livraison.app.module.commande.exception.UnableToUpdateCommandeEtat;
import com.vinci.livraison.app.module.commande.repository.CommandeRepository;
import com.vinci.livraison.app.module.commande.service.ICommandeClientService;
import com.vinci.livraison.app.module.commande.service.ICommandeRestaurateurService;
import com.vinci.livraison.app.module.commande.service.ICommandeService;
import com.vinci.livraison.app.module.livreur.entity.Livreur;
import com.vinci.livraison.app.module.restaurateur.entity.Restaurateur;
import com.vinci.livraison.app.security.authentications.UserType;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.vinci.livraison.app.module.commande.entity.Commande.CanceledBy.CLIENT;
import static com.vinci.livraison.app.module.commande.entity.Commande.CanceledBy.RESTAURATEUR;
import static com.vinci.livraison.app.module.commande.entity.Commande.Etat.*;

@Service
@Transactional
@AllArgsConstructor
public class CommandeService implements ICommandeService, ICommandeClientService, ICommandeRestaurateurService {

    CommandeRepository commande$;
    ArticleRepository article$;


    @Override
    public Commande createCommande(Client client, Restaurateur restaurateur, CreateCommandeForm form) {

        Commande commande = new Commande();
        commande.setClient(client);
        commande.setRestaurateur(restaurateur);
        commande.setDateHeureCreation(LocalDateTime.now());
        if( (form instanceof CreateClientCommandeForm) ){
            String commentaire = ( (CreateClientCommandeForm) form ).getCommentaire();
            commande.setCommentaire(commentaire);
        }


        Set<LigneCommande> lignesCommande = form.getLignesCommande().stream().map(ligneCommande ->
                article$.findArticleByIdAndRestaurateur(ligneCommande.getIdArticle(), restaurateur)
                        .map(article -> new LigneCommande(commande, article, ligneCommande.getQuantite()))
                        .orElse(null)
        )
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        if (lignesCommande.isEmpty()) {
            throw new CommandeCantHaveNoLigneCommande("la commande doit contenir au moins un article");
        }

        double prix = lignesCommande
                .stream()
                .mapToDouble(value -> value.getQuantite() * value.getArticle().getPrix())
                .sum();

        commande.setLigneCommandes(lignesCommande);
        commande.setPrixTotal(prix);

        return commande$.save(commande);
    }

    @Override
    public Optional<Commande> findCommandeByIdAndClient(long id, Client client) {
        return commande$.findByIdAndClientAndRestaurateurShutDownFalse(id, client);
    }

    @Override
    public Page<Commande> findCreatedCommandesByClient(Client client, Pageable pageable) {
        return commande$.findAllByRestaurateurShutDownFalseAndClientAndEtat(client, CREER, pageable);
    }

    @Override
    public Page<Commande> findPendingCommandesByClient(Client client, Pageable pageable) {
        List<Commande.Etat> etats = Arrays.asList(EN_COURS_PREPARATION, EN_ATTENDE_LIVREUR, EN_ATTENDE_LIVRAISON);
        return commande$.findAllByRestaurateurShutDownFalseAndClientAndEtatIsIn(client, etats, pageable);
    }

    @Override
    public Page<Commande> findLivredCommandesByClient(Client client, Pageable pageable) {
        return commande$.findAllByRestaurateurShutDownFalseAndClientAndEtat(client, LIVREE, pageable);
    }

    @Override
    public Commande annulerCommandeByClient(Commande commande) {
        if (!commande.getEtat().equals(CREER)) {
            throw new UnableToUpdateCommandeEtat("tu ne peux pas annuler la commande [ l'état actuel de la commande : " + commande.getEtat().name() + " ]");
        }

        commande.setDateHeurAnnulation(LocalDateTime.now());
        commande.setEtat(ANNULEE);
        commande.setCanceledBy(CLIENT);
        commande.setClosed(true);

        return commande$.save(commande);
    }

    @Override
    public Commande refuserLaCommande(Commande commande, String motifs) {

        if (!commande.getEtat().equals(EN_ATTENDE_LIVRAISON)) {
            throw new UnableToUpdateCommandeEtat("tu ne peux pas refuser Livraison [ l'état actuel de la commande : " + commande.getEtat().name() + " ]");
        }

        commande.setDateHeurLivraison(LocalDateTime.now());
        commande.setEtat(LIVREE);
        commande.setClosed(true);

        return commande$.save(commande);

    }

    @Override
    public Commande accuserLaReceptionDeLivraison(Commande commande, Byte scoreRestaurateur, Byte scoreLivreur) {
        if (!commande.getEtat().equals(EN_ATTENDE_LIVRAISON)) {
            throw new UnableToUpdateCommandeEtat("tu ne peux pas assucer la reception de Livraison [ l'état actuel de la commande : " + commande.getEtat().name() + " ]");
        }

        commande.setDateHeurLivraison(LocalDateTime.now());
        commande.setEtat(LIVREE);
        commande.setClosed(true);

        return commande$.save(commande);
    }


    @Override
    public Optional<Commande> findCommandeByIdAndRestaurateur(long id, Restaurateur restaurateur) {
        return commande$.findByIdAndRestaurateur(id, restaurateur);
    }

    @Override
    public Page<Commande> findCreatedCommandesByRestaurateur(Restaurateur restaurateur, Pageable pageable) {
        return commande$.findAllByRestaurateurAndEtat(restaurateur, CREER, pageable);
    }

    @Override
    public Commande annulerCommandeByRestaurateur(Commande commande) {
        if (!commande.getEtat().equals(CREER)) {
            throw new UnableToUpdateCommandeEtat("tu ne peux pas annuller la commande [ l'état actuel de la commande : " + commande.getEtat().name() + " ]");
        }

        commande.setCanceledBy(RESTAURATEUR);
        commande.setDateHeurAnnulation(LocalDateTime.now());
        commande.setEtat(ANNULEE);

        return commande$.save(commande);
    }

    @Override
    public Commande approverCommande(Commande commande) {

        if (!commande.getEtat().equals(CREER)) {
            throw new UnableToUpdateCommandeEtat("tu ne peux pas approver la commande [ l'état actuel de la commande : " + commande.getEtat().name() + " ]");
        }
        commande.setDateHeurPreparation(LocalDateTime.now());
        commande.setEtat(EN_COURS_PREPARATION);
        return commande$.save(commande);

    }

    @Override
    public Page<Commande> findCommandesEnCoursDePreparationCommandesByRestaurateur(Restaurateur restaurateur, Pageable pageable) {
        return commande$.findAllByRestaurateurAndEtat(restaurateur, EN_COURS_PREPARATION, pageable);
    }

    @Override
    public Commande commandePrete(Commande commande) {
        if (!commande.getEtat().equals(EN_COURS_PREPARATION)) {
            throw new UnableToUpdateCommandeEtat("tu ne peux pas passer la commande pour livraison [ l'état actuel de la commande : " + commande.getEtat().name() + " ]");
        }
        commande.setDateHeurAttendeLivreur(LocalDateTime.now());
        commande.setEtat(EN_ATTENDE_LIVREUR);
        return commande$.save(commande);
    }

    @Override
    public Page<Commande> findCommandesEnAttendeLivreurByRestaurateur(Restaurateur restaurateur, Pageable pageable) {
        return commande$.findAllByRestaurateurAndEtat(restaurateur, EN_ATTENDE_LIVREUR, pageable);
    }

    @Override
    public Commande affecterCommandeAuLiveur(Commande commande, Livreur livreur) {

        if (!commande.getEtat().equals(EN_ATTENDE_LIVREUR)) {
            throw new UnableToUpdateCommandeEtat("tu ne peux pas passer la commande au livreur [ l'état actuel de la commande : " + commande.getEtat().name() + " ]");
        }

        if (!commande.getRestaurateur().equals(livreur.getRestaurateur())) {
            throw new UnableToUpdateCommandeEtat("Liveur n'appartient pas au restaurateur");
        }

        commande.setEtat(EN_ATTENDE_LIVRAISON);
        commande.setLivreur(livreur);
        commande.setDateHeurAttendeLivraison(LocalDateTime.now());
        return commande$.save(commande);


    }

    @Override
    public Page<Commande> findCommandesEnAttendeLivraisonByRestaurateur(Restaurateur restaurateur, Pageable pageable) {
        return commande$.findAllByRestaurateurAndEtat(restaurateur, EN_ATTENDE_LIVRAISON, pageable);
    }

    @Override
    public Page<Commande> findLivredCommandesByRestaurateur(Restaurateur restaurateur, Pageable pageable) {
        return commande$.findAllByRestaurateurAndEtat(restaurateur, LIVREE, pageable);
    }

    public void closeUnansweredCommande() {
        commande$.closeUnrespondedCommandeBefore(CREER,LocalDateTime.now().minusHours(1));
    }
}
