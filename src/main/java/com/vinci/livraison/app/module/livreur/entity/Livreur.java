package com.vinci.livraison.app.module.livreur.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vinci.livraison.app.module.restaurateur.entity.Restaurateur;
import com.vinci.livraison.app.module.shared.User;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Livreur extends User {

    @Column(nullable = false, unique = true)
    private String cin;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false, unique = true)
    private String tel;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_restaurateur")
    private Restaurateur restaurateur;


    public Livreur(String login, String password, String cin, String nom, String tel) {
        super(login, password);
        this.cin = cin;
        this.nom = nom;
        this.tel = tel;
    }
}
