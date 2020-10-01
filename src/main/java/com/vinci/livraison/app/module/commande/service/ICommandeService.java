package com.vinci.livraison.app.module.commande.service;

import com.vinci.livraison.app.module.client.entity.Client;
import com.vinci.livraison.app.module.commande.CreateCommandeForm;
import com.vinci.livraison.app.module.commande.entity.Commande;
import com.vinci.livraison.app.module.restaurateur.entity.Restaurateur;

public interface ICommandeService {

    Commande createCommande(Client client, Restaurateur restaurateur, CreateCommandeForm form);
}
