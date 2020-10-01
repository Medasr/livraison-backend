package com.vinci.livraison.app.module.livreur.service.impl;

import com.vinci.livraison.app.module.livreur.CreateLivreurForm;
import com.vinci.livraison.app.module.livreur.entity.Livreur;
import com.vinci.livraison.app.module.livreur.repository.LivreurRepository;
import com.vinci.livraison.app.module.livreur.service.ILivreurService;
import com.vinci.livraison.app.module.restaurateur.entity.Restaurateur;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
@AllArgsConstructor
public class LivreurService implements ILivreurService {

    LivreurRepository livreur$;
    PasswordEncoder encoder;


    @Override
    public Optional<Livreur> findLivreurById(long id) {
        return livreur$.findById(id);
    }

    @Override
    public Optional<Livreur> findLivreurByIdAndRestaurateur(long id, Restaurateur restaurateur) {
        return livreur$.findByIdAndRestaurateur(id,restaurateur);
    }


    @Override
    public Page<Livreur> findLivreursByRestaurateur(Restaurateur restaurateur, Pageable pageable) {
        return livreur$.findAllByRestaurateur(restaurateur, pageable);
    }

    @Override
    public Livreur createLivreur(Restaurateur restaurateur, CreateLivreurForm form) {

        Livreur livreur = new Livreur(form.getLogin(), form.getPassword(), form.getCin(), form.getNom(),form.getTel());

        livreur.setRestaurateur(restaurateur);

        return livreur$.save(livreur);
    }

    @Override
    public Livreur updateNumTel(Livreur livreur, String tel) {

        livreur.setTel(tel);
        return livreur$.save(livreur);

    }

    @Override
    public Livreur updatePassword(Livreur livreur, String password, String newPassword) {

        if(!encoder.matches(password, livreur.getPassword())){
            throw new BadCredentialsException("Mot de passe non valide");
        }

        livreur.setPassword(encoder.encode(newPassword));

        return livreur$.save(livreur);
    }
}
