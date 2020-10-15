package com.vinci.livraison.app.module.article.service.impl;


import com.vinci.livraison.app.module.article.CreateMenuForm;
import com.vinci.livraison.app.module.article.CreateProduitForm;
import com.vinci.livraison.app.module.article.entity.Article;
import com.vinci.livraison.app.module.article.entity.Categorie;
import com.vinci.livraison.app.module.article.entity.ProdCat;
import com.vinci.livraison.app.module.article.entity.ProdMenu;
import com.vinci.livraison.app.module.article.exception.*;
import com.vinci.livraison.app.module.article.repository.ArticleRepository;
import com.vinci.livraison.app.module.article.repository.CategorieRepository;
import com.vinci.livraison.app.module.article.repository.ProdCategorieRepository;
import com.vinci.livraison.app.module.article.repository.ProdMenuRepository;
import com.vinci.livraison.app.module.article.service.IArticleService;
import com.vinci.livraison.app.module.article.service.IMenuService;
import com.vinci.livraison.app.module.article.service.IPruduitService;
import com.vinci.livraison.app.module.restaurateur.entity.Restaurateur;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class ArticleService implements IArticleService, IPruduitService, IMenuService {

    ArticleRepository article$;

    ProdCategorieRepository prodCat$;

    ProdMenuRepository prodMenu$;

    CategorieRepository categorie$;

    @Override
    public Optional<Article> findArticleByIdAndRestaurateur(Long idArticle, Restaurateur restaurateur) {
        return article$.findArticleByIdAndRestaurateur(idArticle, restaurateur);
    }

    @Override
    public Page<Article> findArticlesByRestaurateur(Restaurateur restaurateur, Pageable pageable) {
        return article$.findArticlesByRestaurateur(restaurateur, pageable);
    }

    @Override
    public Page<Article> findArticlesByCategorie(Categorie categorie, Pageable pageable) {
        return article$.findArticlesByCategorie(categorie, pageable);
    }


    @Override
    public Article loadArticleCategories(Article article) {
        prodCat$.findAllByArticle(article)
                .stream()
                .map(ProdCat::getCategorie)
                .forEach(categorie -> article.getCategories().add(categorie));

        return article;
    }

    @Override
    public Article addCategorieToExistingArticle(Article article, Categorie categorie) {

        if (prodCat$.existsByArticleAndCategorie(article, categorie)) {

            throw new ProdCatAlreadyExistsException("Categorie est deja ");
        }

        ProdCat prodCat = new ProdCat(article, categorie);

        prodCat$.save(prodCat);
        return article;

    }

    @Override
    public void removeCategorieFromExistingArticle(Article article, Categorie categorie) {

        prodCat$.findByArticleAndCategorie(article, categorie).map(prodCat1 -> {

            if (prodCat$.countByArticle(article) > 1) {
                throw new ArticleCantHaveNoCategorie("Article doit appartenir au moins a une seul categorie");
            }

            prodCat$.delete(prodCat1);
            return prodCat1;
        }).orElseThrow(() ->
                new ProdCatNotExistsException("article #[" + article.getId() + "] n'appartient pas au catégorie #[" + categorie.getId() + "] ")
        );

    }

    @Override
    public Article createProduit(Restaurateur restaurateur, CreateProduitForm form) {

        Article article = new Article();
        article.setMenu(false);
        article.setTitre(form.getTitre());
        article.setPrix(form.getPrix());

        // pour affecter L'id
        article$.save(article);

        List<ProdCat> ProdCats = categorie$.findCategoriesByRestaurateurAndIdIn(restaurateur, form.getCategories())
                .stream()
                .map(cat -> {
                    article.getCategories().add(cat);
                    return new ProdCat(article, cat);
                })
                .collect(Collectors.toList());

        if (ProdCats.isEmpty()) {
            throw new ArticleCantHaveNoCategorie("Article doit appartenir au moins a une seul categorie");
        }

        prodCat$.saveAll(ProdCats);

        return article;
    }

    @Override
    public Optional<Article> findProduitByIdAndRestaurateur(Long idProduit, Restaurateur restaurateur) {
        return article$.findProduitByIdAndRestaurateur(idProduit, restaurateur);
    }

    @Override
    public Page<Article> findProduitsByRestaurateur(Restaurateur restaurateur, Pageable pageable) {
        return article$.findProduitsByRestaurateur(restaurateur, pageable);
    }

    @Override
    public Article createMenu(Restaurateur restaurateur, CreateMenuForm form) {

        Article menu = new Article();
        menu.setMenu(true);
        menu.setTitre(form.getTitre());
        menu.setPrix(form.getPrix());

        // pour affecter L'id
        article$.save(menu);

        List<ProdCat> ProdCats = categorie$.findCategoriesByRestaurateurAndIdIn(restaurateur, form.getCategories())
                .stream()
                .map(cat -> {
                    menu.getCategories().add(cat);
                    return new ProdCat(menu, cat);
                })
                .collect(Collectors.toList());

        if (ProdCats.isEmpty()) {
            throw new ArticleCantHaveNoCategorie("Menu doit appartenir au moins a une seul categorie");
        }

        prodCat$.saveAll(ProdCats);

        List<ProdMenu> prodMenus = article$.findProduitsByIdInRestaurateur(form.getProduits(), restaurateur)
                .stream()
                .map(article1 -> {
                    menu.getProduits().add(article1);
                    return new ProdMenu(menu, article1);
                }).collect(Collectors.toList());

        if (prodMenus.isEmpty()) {
            throw new MenuCantHaveNoProduit("Menu doit contient au moins un seul produit");
        }

        prodMenu$.saveAll(prodMenus);

        return menu;
    }

    @Override
    public Optional<Article> findMenuByIdAndRestaurateur(Long idMenu, Restaurateur restaurateur) {
        return article$.findMenuByIdAndRestaurateur(idMenu, restaurateur);
    }

    @Override
    public Page<Article> findMenusByRestaurateur(Restaurateur restaurateur, Pageable pageable) {
        return article$.findMenusByRestaurateur(restaurateur, pageable);
    }

    @Override
    public Article loadMenuProduits(Article menu) {
        Set<Article> articles = prodMenu$.findAllByMenu(menu)
                .stream()
                .map(ProdMenu::getProduit)
                .collect(Collectors.toSet());

        menu.setProduits(articles);
        return menu;
    }

    @Override
    public Article addProduitToExistingMenu(Article Menu, Article Produit) {
        if (prodMenu$.existsByMenuAndProduit(Menu, Produit)) {

            throw new ProdMenuAlreadyExistsException("");
        }

        ProdMenu prodmenu = new ProdMenu(Menu, Produit);

        prodMenu$.save(prodmenu);
        return Menu;
    }

    @Override
    public void removeProduitFromExistingMenu(Article Menu, Article Produit) {

        prodMenu$.findByMenuAndProduit(Menu, Produit).map(prodmenu -> {

            if (prodMenu$.countByMenu(Menu) > 1) {
                throw new MenuCantHaveNoProduit("Menu doit contient au moins un seul Produit");
            }

            prodMenu$.delete(prodmenu);
            return prodmenu;
        }).orElseThrow(() ->
                new ProdMenuNotExistsException("Menu #[" + Menu.getId() + "] ne contient pas produit #[" + Produit.getId() + "] ")
        );

    }
}
