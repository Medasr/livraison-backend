package com.vinci.livraison.app.module.article.service;

import com.vinci.livraison.app.module.article.CreateMenuForm;
import com.vinci.livraison.app.module.article.entity.Article;
import com.vinci.livraison.app.module.restaurateur.entity.Restaurateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IMenuService {

    Article createMenu(CreateMenuForm form);

    Optional<Article> findMenuByIdAndRestaurateur(Long idMenu , Restaurateur restaurateur);

    Page<Article> findMenusByRestaurateur(Restaurateur restaurateur,Pageable pageable);

    Article loadMenuProduits(Article menu);

    Article addProduitToMenu(Article Menu,Article Produit);

    void removeProduitFRomMenu(Article Menu,Article Produit);

}