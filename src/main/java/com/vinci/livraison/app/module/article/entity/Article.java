package com.vinci.livraison.app.module.article.entity;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;


@Entity
@NoArgsConstructor
@Getter
@Setter
public class Article implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(nullable = false)
    private String titre;

    @Column(name = "is_menu", nullable = false, updatable = false)
    private boolean menu;

    @Column(nullable = false, columnDefinition = "DECIMAL(10,2) DEFAULT 0")
    private double prix;

    @Transient
    private Set<Categorie> categories;

    @Transient
    private Set<Article> produits;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass() || id == null) return false;

        Article article = (Article) o;

        return id.equals(article.id);
    }

    @Override
    public int hashCode() {
        if (id == null) return 0;
        return id.hashCode();
    }
}
