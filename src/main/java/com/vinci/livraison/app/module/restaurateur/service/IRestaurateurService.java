package com.vinci.livraison.app.module.restaurateur.service;

import com.vinci.livraison.app.module.enseigne.CreateEnseigneForm;
import com.vinci.livraison.app.module.enseigne.entity.Enseigne;
import com.vinci.livraison.app.module.restaurateur.CreateRestaurateurForm;
import com.vinci.livraison.app.module.restaurateur.entity.Restaurateur;
import com.vinci.livraison.app.module.restaurateur.entity.Ville;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface IRestaurateurService {

    Restaurateur createRestaurateur(Enseigne enseigne, CreateRestaurateurForm form, boolean isEnseigne);

    Restaurateur createRestaurateur(Enseigne enseigne, CreateRestaurateurForm form);

    List<Restaurateur> createRestaurateurs(Enseigne enseigne, List<CreateRestaurateurForm> forms);

    Optional<Restaurateur> findActiveRestaurateurById(Long id);

    Optional<Restaurateur> findRestaurateurWithScore(Long id);

    Optional<Restaurateur> findRestaurateurByIdAnEnseinge(Long id, Enseigne enseigne);

    Optional<Restaurateur> findRestaurateuByEnseingeWithScore(Long id, Enseigne enseigne);

    Page<Restaurateur> findRestaurateursByVille(Ville ville, Pageable pageable);

    Page<Restaurateur> findRestaurateursByEnseigne(Enseigne enseigne, Pageable pageable);

    Page<Restaurateur> findRestaurateursByEnseigne(Enseigne enseigne, boolean shutdown, Pageable pageable);


}
