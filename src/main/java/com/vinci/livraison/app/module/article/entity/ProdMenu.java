package com.vinci.livraison.app.module.article.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.util.Assert;

import javax.persistence.*;
import java.io.Serializable;


@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = {"id_menu", "id_produit"})
})
public class ProdMenu implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    public ProdMenu(Article menu, Article produit) {
        Assert.isTrue(!menu.isMenu(), "");
        Assert.isTrue(produit.isMenu(), "");
        this.menu = menu;
        this.produit = produit;
    }

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_menu")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Article menu;

    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_produit")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Article produit;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass() || id == null) return false;

        ProdMenu obj = (ProdMenu) o;

        return id.equals(obj.id);
    }

    @Override
    public int hashCode() {
        if (id == null) return 0;
        return id.hashCode();
    }


}
