package com.vinci.livraison.app.module.article.entity;

import com.vinci.livraison.app.module.restaurateur.entity.Restaurateur;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Categorie implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(nullable = false)
    private String titre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_categorie_mere")
    private Categorie categorieMere;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_restaurateur", nullable = false, updatable = false)
    private Restaurateur restaurateur;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass() || id == null) return false;

        Categorie obj = (Categorie) o;

        return id.equals(obj.id);
    }

    @Override
    public int hashCode() {
        if (id == null) return 0;
        return id.hashCode();
    }
}
