package com.vinci.livraison.app.module.commande.service;

import com.vinci.livraison.app.module.client.entity.Client;
import com.vinci.livraison.app.module.commande.entity.Commande;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ICommandeClientService {

    Optional<Commande> findCommandeByIdAndClient(long id, Client client);



    Page<Commande> findCreatedCommandesByClient(Client client, Pageable pageable);

    Page<Commande> findPendingCommandesByClient(Client client, Pageable pageable);

    Page<Commande> findLivredCommandesByClient(Client client, Pageable pageable);

    Commande annulerCommandeByClient(Commande commande);

    Commande refuserLaCommande(Commande commande,String motifs);

    Commande accuserLaReceptionDeLivraison(Commande commande,Byte scoreRestaurateur,Byte scoreLivreur);


}
