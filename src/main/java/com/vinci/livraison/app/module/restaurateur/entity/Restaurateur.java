package com.vinci.livraison.app.module.restaurateur.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.vinci.livraison.app.module.enseigne.entity.Enseigne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Restaurateur implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(nullable = false)
    private String nom;

    @Column(nullable = false)
    private String adresse;

    @ManyToOne
    @JoinColumn(name = "id_ville", nullable = false)
    private Ville ville;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_enseigne")
    private Enseigne enseigne;

    @JsonIgnore
    @Column(name = "is_enseigne", nullable = false)
    private boolean isenseigne;

    @Column(name = "shut_down", nullable = false)
    private boolean shutDown = false;

    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "id_restaurateur_user")
    private RestaurateurUser restaurateurUser;

    @JsonIgnore
    @OneToMany(mappedBy = "restaurateur", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RestaurateurType> RestaurateurTypes = new HashSet<>();

    @Transient
    private Set<Type> types;

    @Transient
    private Double scoreRestaurateur;

    @Transient
    private Double scoreLivreur;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass() || id == null) return false;

        Restaurateur obj = (Restaurateur) o;

        return id.equals(obj.id);
    }

    @Override
    public int hashCode() {
        if (id == null) return 0;
        return id.hashCode();
    }


}
