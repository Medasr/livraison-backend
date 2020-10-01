package com.vinci.livraison.app.module.commande.service;

import com.vinci.livraison.app.module.client.entity.Client;
import com.vinci.livraison.app.module.commande.CreateCommandeForm;
import com.vinci.livraison.app.module.commande.entity.Commande;
import com.vinci.livraison.app.module.restaurateur.entity.Restaurateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ICommandeClientService {

    Page<Commande> findCreatedCommandesByClient(Client client, Pageable pageable);

    Page<Commande> findPendingCommandesByClient(Client client, Pageable pageable);

    Page<Commande> findLivredCommandesByClient(Client client, Pageable pageable);

    Commande createClientCommande(Client client, Restaurateur restaurateur, CreateCommandeForm form);


}
