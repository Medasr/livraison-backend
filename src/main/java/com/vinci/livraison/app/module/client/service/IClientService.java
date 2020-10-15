package com.vinci.livraison.app.module.client.service;

import com.vinci.livraison.app.module.client.CreateAdresseForm;
import com.vinci.livraison.app.module.client.CreateClientForm;
import com.vinci.livraison.app.module.client.GeoLocationForm;
import com.vinci.livraison.app.module.client.entity.AdresseLivraison;
import com.vinci.livraison.app.module.client.entity.Client;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface IClientService {

    Optional<Client> findClientById(long id);

    Page<Client> findAllClients(Pageable pageable);

    Client updatePassword(Client client, String oldPassword, String newPassword);

    Client updateLogin(Client client, String login);

    Client updateClient(Client client, String tel);

    Client createClient(CreateClientForm clientForm);

    AdresseLivraison createClientAdresse(Client client, CreateAdresseForm form);

    AdresseLivraison updateAdresse(AdresseLivraison adresseLivraison,CreateAdresseForm form);

    AdresseLivraison setAdresseAsPrimary(AdresseLivraison adresseLivraison);

    AdresseLivraison updateGeoLocation(AdresseLivraison adresseLivraison, GeoLocationForm form);


}
