package com.vinci.livraison.app.module.article.repository;


import com.vinci.livraison.app.module.article.entity.Article;
import com.vinci.livraison.app.module.article.entity.ProdCat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;


@Repository
public interface ProdCategorieRepository extends JpaRepository<ProdCat, Long> {


}
