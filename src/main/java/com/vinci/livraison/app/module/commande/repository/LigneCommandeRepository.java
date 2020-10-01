package com.vinci.livraison.app.module.commande.repository;

import com.vinci.livraison.app.module.article.entity.Article;
import com.vinci.livraison.app.module.commande.entity.Commande;
import com.vinci.livraison.app.module.commande.entity.LigneCommande;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LigneCommandeRepository extends JpaRepository<LigneCommande, Long> {

    boolean existsByArticleAndCommande(Article article, Commande commande);

}
