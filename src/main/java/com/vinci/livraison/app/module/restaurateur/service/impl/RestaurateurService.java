package com.vinci.livraison.app.module.restaurateur.service.impl;


import com.vinci.livraison.app.module.enseigne.CreateEnseigneForm;
import com.vinci.livraison.app.module.enseigne.entity.Enseigne;
import com.vinci.livraison.app.module.enseigne.repository.EnseigneRepository;
import com.vinci.livraison.app.module.restaurateur.CreateRestaurateurForm;
import com.vinci.livraison.app.module.restaurateur.entity.*;
import com.vinci.livraison.app.module.restaurateur.repository.RestaurateurRepository;
import com.vinci.livraison.app.module.restaurateur.repository.RestaurateurUserRepository;
import com.vinci.livraison.app.module.restaurateur.repository.TypeRepository;
import com.vinci.livraison.app.module.restaurateur.repository.VilleRepository;
import com.vinci.livraison.app.module.restaurateur.service.IRestaurateurService;
import com.vinci.livraison.app.module.shared.exception.ValidationException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
@Transactional
public class RestaurateurService implements IRestaurateurService {

    RestaurateurRepository restaurateur$;

    VilleRepository ville$;

    TypeRepository type$;

    EnseigneRepository enseigne$;

    RestaurateurUserRepository user$;

    PasswordEncoder encoder;

    @Override
    public Restaurateur createRestaurateur(Enseigne enseigne, CreateEnseigneForm.Restaurateur form) {
        Map<String, Object> errors = new HashMap<>();

        Ville ville = ville$.findById(form.getIdVille()).orElse(null);
        if (ville == null) {
            errors.put("idVille", "Ville n'esxiste pas");
        }

        List<Type> types = type$.findAllById(form.getTypes());

        if (types.isEmpty()) {
            errors.put("types", "vous devez fournir au moins un type");
        }

        if (user$.existsByLogin(form.getLogin())) {
            errors.put("login", "login deja existe");
        }

        if (errors.isEmpty()) {
            Restaurateur restaurateur = new Restaurateur();
            createRestaurateur(restaurateur, ville, new HashSet<>(types), enseigne);

            RestaurateurUser user = new RestaurateurUser();
            user.setDateCreation(LocalDateTime.now());
            user.setLogin(form.getLogin());
            user.setPassword(encoder.encode(form.getPassword()));

            restaurateur.setRestaurateurUser(user);
            restaurateur.setIsenseigne(true);
            restaurateur.setNom(form.getNom());
            restaurateur.setAdresse(form.getAdresse());
            restaurateur.setShutDown(false);

            return restaurateur$.save(restaurateur);
        }


        throw new ValidationException("invalid", errors);


    }

    @Override
    public Restaurateur createRestaurateur(Enseigne enseigne, CreateRestaurateurForm form) {
        return null;
    }

    private Restaurateur createRestaurateur(Restaurateur restaurateur, Ville ville, Set<Type> types, Enseigne enseigne) {

        restaurateur.setVille(ville);
        restaurateur.setEnseigne(enseigne);
        restaurateur.setTypes(types);
        types.forEach(type -> restaurateur
                .getRestaurateurTypes()
                .add(new RestaurateurType(type, restaurateur))
        );

        return restaurateur;
    }

    @Override
    public List<Restaurateur> createRestaurateurs(Enseigne enseigne, List<CreateRestaurateurForm> forms) {

        List<Restaurateur> restaurateurs = new ArrayList<>();
        Map<String, Object> errors = new HashMap<>();

        for (int i = 0; i < forms.size(); i++) {
            final CreateRestaurateurForm form = forms.get(i);

            try {
                restaurateurs.add(createRestaurateur(enseigne, form));
            } catch (ValidationException e) {
                errors.put(i + "", e.getErrors());
            }
        }

        if (errors.isEmpty()) {

        }

        return restaurateurs;

    }

    @Override
    public Optional<Restaurateur> findRestaurateurById(Long id) {
        return restaurateur$.findRestaurateurByShutDownIsFalseAndId(id)
                .map(restaurateur -> {
                    restaurateur.setTypes(restaurateur
                            .getRestaurateurTypes()
                            .stream()
                            .map(RestaurateurType::getType)
                            .collect(Collectors.toSet()));
                    return restaurateur;

                });
    }

    @Override
    public Optional<Restaurateur> findRestaurateurWithScore(Long id) {
        return findRestaurateurById(id).map(restaurateur -> {
            restaurateur$.getRestaurateurScore(restaurateur)
                    .ifPresent(score -> {
                        restaurateur.setScoreRestaurateur(score.getScoreRestaurateur());
                        restaurateur.setScoreLivreur(score.getScoreLivreur());
                    });
            return restaurateur;
        });
    }

    @Override
    public Optional<Restaurateur> findRestaurateurByIdAnEnseinge(Long id, Enseigne enseigne) {
        return restaurateur$.findRestaurateurByIdAndEnseigne(id, enseigne);
    }

    @Override
    public Optional<Restaurateur> findRestaurateuByEnseingeWithScore(Long id, Enseigne enseigne) {
        return findRestaurateurByIdAnEnseinge(id, enseigne).map(restaurateur -> {
            restaurateur$.getRestaurateurScore(restaurateur)
                    .ifPresent(score -> {
                        restaurateur.setScoreRestaurateur(score.getScoreRestaurateur());
                        restaurateur.setScoreLivreur(score.getScoreLivreur());
                    });
            return restaurateur;
        });
    }

    @Override
    public Page<Restaurateur> findRestaurateursByVille(Ville ville, Pageable pageable) {
        return restaurateur$.findRestaurateursByShutDownIsFalseAndVille(ville, pageable);
    }

    @Override
    public Page<Restaurateur> findRestaurateursByEnseigne(Enseigne enseigne, Pageable pageable) {
        return restaurateur$.findRestaurateursByEnseigne(enseigne, pageable);
    }

    @Override
    public Page<Restaurateur> findRestaurateursByEnseigne(Enseigne enseigne, boolean shutdown, Pageable pageable) {
        return restaurateur$.findAllByEnseigneAndShutDown(enseigne, shutdown, pageable);
    }


}
