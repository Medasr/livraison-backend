package com.vinci.livraison.app.module.enseigne.service;

import com.vinci.livraison.app.module.enseigne.CreateEnseigneForm;
import com.vinci.livraison.app.module.enseigne.entity.Enseigne;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface IEnseigneService {

    Enseigne insert(CreateEnseigneForm form);

    Enseigne activeOrDesactiveEnseigne(Enseigne enseigne,boolean isActive);

    Optional<Enseigne> getEnseigneById(Long id);

    Page<Enseigne> getAllEnseignes(int page,int size);

    Page<Enseigne> getAllActiveEnseignes(int page,int size);

    Page<Enseigne> getAllDesativeEnseignes(int page,int size);

}
