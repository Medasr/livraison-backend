package com.vinci.livraison.app.module.commande.entity;

import com.vinci.livraison.app.module.article.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_commande","id_article"})
})
public class LigneCommande implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    public LigneCommande(Commande commande, Article article, byte quantite) {
        this.commande = commande;
        this.article = article;
        this.quantite = quantite;
    }


    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_commande")
    private Commande commande;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_article")
    private Article article;

    private byte quantite;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if ( o == null || getClass() != o.getClass() || id == null ) return false;

        LigneCommande obj = (LigneCommande) o;

        return id.equals(obj.id);
    }

    @Override
    public int hashCode() {
        if (id == null) return 0;
        return id.hashCode();
    }

}
