package com.vinci.livraison.app.module.restaurateur.repository;


import com.vinci.livraison.app.module.enseigne.entity.Enseigne;
import com.vinci.livraison.app.module.restaurateur.Score;
import com.vinci.livraison.app.module.restaurateur.entity.Restaurateur;
import com.vinci.livraison.app.module.restaurateur.entity.Ville;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RestaurateurRepository extends JpaRepository<Restaurateur, Long> {

    Page<Restaurateur> findRestaurateursByEnseigne(Enseigne enseigne, Pageable pageable);

    Page<Restaurateur> findRestaurateursByShutDownIsFalseAndVille(Ville ville, Pageable pageable);

    // find active one by id
    @EntityGraph(attributePaths = {"restaurateurTypes.type", "restaurateurUser", "ville"})
    Optional<Restaurateur> findRestaurateurByShutDownIsFalseAndId(Long id);


    @Modifying
    @Query("update Restaurateur as r set r.shutDown = true where r.enseigne = ?1")
    long shutdownResteurateursByEnseigne(Enseigne enseigne);

    @Query("SELECT new com.vinci.livraison.app.module.restaurateur.Score( AVG(c.scoreRestaurateur)  , AVG(c.scoreLivreur) ) from Commande c  where c.restaurateur = ?1 GROUP BY c.restaurateur")
    Optional<Score> getRestaurateurScore(Restaurateur restaurateur);


    // Get activate/deactivate Restaurateurs by there Enseigne
    Page<Restaurateur> findAllByEnseigneAndShutDown(Enseigne enseignen, boolean shutdown, Pageable pageable);

    @EntityGraph(attributePaths = {"restaurateurTypes.type", "restaurateurUser", "ville"})
    Optional<Restaurateur> findByRestaurateurUserId(long id);

    @EntityGraph(attributePaths = {"restaurateurTypes.type", "restaurateurUser", "ville"})
    Optional<Restaurateur> findByRestaurateurUserLogin(String login);

    // nbr of Active Restaurateurs by enseigne
    long countByEnseigneAndShutDown(Enseigne enseigne, boolean shutdown);


    long countByEnseigne(Enseigne enseigne);

    //nbr of active Restaurateurs
    long countAllByShutDown(boolean shutdown);

    @EntityGraph(attributePaths = {"restaurateurTypes.type", "restaurateurUser", "ville"})
    Optional<Restaurateur> findRestaurateurByIdAndEnseigne(Long id, Enseigne enseigne);
}
