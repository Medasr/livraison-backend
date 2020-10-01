package com.vinci.livraison.app.module.article.repository;

import com.vinci.livraison.app.module.article.entity.Categorie;
import com.vinci.livraison.app.module.restaurateur.entity.Restaurateur;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface CategorieRepository extends JpaRepository<Categorie,Long> {


    List<Categorie> findCategoriesByCategorieMere(Categorie parent);

    Optional<Categorie> findCategorieByIdAndRestaurateur(long id, Restaurateur restaurateur);

    Page<Categorie> findCategoriesByRestaurateur(Restaurateur restaurateur, Pageable pageable);

    Set<Categorie> findCategoriesByRestaurateurAndIdIn(Restaurateur restaurateur, Set<Long> ids);


}
