package com.vinci.livraison.app.module.client.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vinci.livraison.app.module.shared.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;


@Entity
@Table(name = "clients")
@NoArgsConstructor
@Getter
@Setter
public class Client extends User {

    private static final long serialVersionUID = -3401423317159025857L;

    @Column(name = "created_by")
    private Long createdBy;

    @Column(nullable = false)
    private String nom;

    private String tel;

    @JsonIgnore
    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<AdresseLivraison> adresses = new HashSet<>();

    public void addAdresse(AdresseLivraison adresse) {
        if (adresse.isPrimary()) {
            Optional<AdresseLivraison> addr = adresses.stream()
                    .filter(AdresseLivraison::isPrimary)
                    .findFirst().map(adr -> {
                        adr.setPrimary(false);
                        return adr;
                    });
        }
        adresse.setClient(this);
        adresses.add(adresse);
    }

    public void deleteAdresse(AdresseLivraison adresse) {
        adresses.removeIf(adresseLivraison -> adresse.getId().equals(adresseLivraison.getId()));
    }

    public AdresseLivraison getPrimaryAdresse() {

        return adresses.stream()
                .filter(AdresseLivraison::isPrimary)
                .findFirst().orElse(null);

    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass() || id == null) return false;

        Client obj = (Client) o;

        return id.equals(obj.id);
    }

    @Override
    public int hashCode() {
        if (id == null) return 0;
        return id.hashCode();
    }

}
