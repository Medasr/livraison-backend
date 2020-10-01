package com.vinci.livraison.app.module.commande.entity;

import com.vinci.livraison.app.module.client.entity.Client;
import com.vinci.livraison.app.module.livreur.entity.Livreur;
import com.vinci.livraison.app.module.restaurateur.entity.Restaurateur;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;


@Entity
@NoArgsConstructor
@Data
public class Commande implements Serializable {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_restaurateur")
    private Restaurateur restaurateur;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_client")
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_livreur")
    private Livreur livreur;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Etat etat;

    @Column(nullable = false, updatable = false)
    private LocalDateTime dateHeureCreation;

    private LocalDateTime dateHeurAnnulation;

    private LocalDateTime dateHeurPreparation;

    private LocalDateTime dateHeurAttendeLivreur;

    private LocalDateTime DateHeurAttendeLivraison;

    private LocalDateTime dateHeurLivraison;

    @OneToMany(mappedBy = "commande", cascade = CascadeType.ALL)
    private Set<LigneCommande> ligneCommandes;

    private Byte scoreLivreur;

    private Byte scoreRestaurateur;

    private boolean livraisonRefusee = false;

    private String motifsRefus;

    private String commentaire;

    private double prixTotal;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if ( o == null || getClass() != o.getClass() || id == null ) return false;

        Commande obj = (Commande) o;

        return id.equals(obj.id);
    }

    @Override
    public int hashCode() {
        if (id == null) return 0;
        return id.hashCode();
    }


    public enum Etat {
            CREER,
            ANNULEE,
            EN_COURS_PREPARATION,
            EN_ATTENDE_LIVREUR,
            EN_ATTENDE_LIVRAISON,
            LIVREE
    }
}



