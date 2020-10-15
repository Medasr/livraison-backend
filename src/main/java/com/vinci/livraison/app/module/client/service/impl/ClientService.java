package com.vinci.livraison.app.module.client.service.impl;

import com.vinci.livraison.app.module.client.CreateAdresseForm;
import com.vinci.livraison.app.module.client.CreateClientForm;
import com.vinci.livraison.app.module.client.GeoLocationForm;
import com.vinci.livraison.app.module.client.entity.AdresseLivraison;
import com.vinci.livraison.app.module.client.entity.Client;
import com.vinci.livraison.app.module.client.repository.AdresseLivraisonRepository;
import com.vinci.livraison.app.module.client.repository.ClientRepository;
import com.vinci.livraison.app.module.client.service.IClientService;
import com.vinci.livraison.app.module.shared.IUserService;
import com.vinci.livraison.app.module.shared.exception.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.vinci.livraison.app.module.client.entity.AdresseLivraison.GeoLocation;

@Service
@Transactional
@AllArgsConstructor
public class ClientService implements IClientService, IUserService<Client> {

    ClientRepository client$;
    AdresseLivraisonRepository adresse$;
    PasswordEncoder encoder;


    @Override
    public Optional<Client> findClientById(long id) {
        return client$.findById(id);
    }

    @Override
    public Page<Client> findAllClients(Pageable pageable){
        return client$.findAll(pageable);
    }

    @Override
    public Client updatePassword(Client client, String oldPassword, String newPassword) {
        if (encoder.matches(oldPassword, client.getPassword())) {

            client.setPassword(encoder.encode(newPassword));
            return client$.save(client);
        }

        throw new RuntimeException("Password incorrect");

    }

    @Override
    public Client updateLogin(Client client, String login) {

        if (client$.existsByLogin(login)) {
            Map<String, Object> errors = new HashMap<>();
            errors.put("login","Email déjà prise");
            throw new ValidationException("Validation échoue",errors);
        }

        client.setLogin(login);
        return client$.save(client);
    }

    @Override
    public Client updateClient(Client client, String tel) {
        client.setTel(tel);
        return client$.save(client);
    }


    @Override
    public Client createClient(CreateClientForm form) {

        Map<String, Object> errors = new HashMap<>();
        if (client$.existsByLogin( form.getLogin() )) {
            errors.put("login","Email déjà prise");
        }

        if(form.getTel() != null && !form.getTel().matches("^(\\+212|0)[5-7]{1}[0-9]{8}$")){
            errors.put("tel","Numéro de téléphone invalide");
        }

        if (!errors.isEmpty()) {
            throw new ValidationException("Validation échoue",errors);
        }

        Client client = new Client();
        client.setNom(form.getNom());
        client.setTel(form.getTel());
        client.setDateCreation(LocalDateTime.now());
        client.setLogin(form.getLogin());
        client.setPassword(encoder.encode(form.getPassword()));

        if (form.getAdresse() != null) {
            AdresseLivraison adresse = new AdresseLivraison();
            adresse.setClient(client);
            adresse.setAdresse(form.getAdresse());
            adresse.setPrimary(true);
            client.getAdresses().add(adresse);
        }


        return client$.save(client);
    }

    @Override
    public AdresseLivraison createClientAdresse(Client client, CreateAdresseForm form) {
        AdresseLivraison adresse = new AdresseLivraison();
        adresse.setAdresse(form.getAdresse());
        if (form.getGeoLocation() != null) {
            GeoLocation geoLocation = new GeoLocation(form.getGeoLocation().getLatitude(), form.getGeoLocation().getLongitude());
        }

        return adresse$.save(adresse);
    }

    @Override
    public AdresseLivraison updateAdresse(AdresseLivraison adresseLivraison, CreateAdresseForm form) {
        return null;
    }

    @Override
    public AdresseLivraison setAdresseAsPrimary(AdresseLivraison adresse) {

        if (adresse.isPrimary()) return adresse;

        adresse$.findByClientIdAndPrimaryTrue(adresse.getClient().getId())
                .ifPresent(adr -> {
                    adr.setPrimary(false);
                    adresse$.save(adr);
                });

        adresse.setPrimary(true);
        return adresse$.save(adresse);

    }

    @Override
    public AdresseLivraison updateGeoLocation(AdresseLivraison adresse, GeoLocationForm form) {

        adresse.setGeoLocation(new AdresseLivraison.GeoLocation(form.getLatitude(), form.getLongitude()));
        return adresse$.save(adresse);
    }

    @Override
    public Optional<Client> findByLogin(String login) {
        return client$.findByLogin(login);
    }
}
