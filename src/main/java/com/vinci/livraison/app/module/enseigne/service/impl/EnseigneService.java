package com.vinci.livraison.app.module.enseigne.service.impl;

import com.vinci.livraison.app.module.enseigne.CreateEnseigneForm;
import com.vinci.livraison.app.module.enseigne.entity.Contract;
import com.vinci.livraison.app.module.enseigne.entity.Enseigne;
import com.vinci.livraison.app.module.enseigne.repository.EnseigneRepository;
import com.vinci.livraison.app.module.enseigne.service.IEnseigneService;
import com.vinci.livraison.app.module.restaurateur.repository.TypeRepository;
import com.vinci.livraison.app.module.restaurateur.repository.VilleRepository;
import com.vinci.livraison.app.module.restaurateur.service.impl.RestaurateurService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.time.LocalDate;
import java.util.Optional;

@Service
@AllArgsConstructor
public class EnseigneService implements IEnseigneService {

    final EnseigneRepository enseigne$;
    final RestaurateurService restaurateur$;
    final VilleRepository ville$;
    final TypeRepository type$;

    @Override
    public Enseigne insert(CreateEnseigneForm form) {
        Enseigne enseigne = new Enseigne();
        Contract contract = new Contract();

        enseigne.setContract(contract);
        contract.setEnseigne(enseigne);
        contract.setDateCreation(LocalDate.now());
        contract.setDateDerniereActivation(LocalDate.now());
        contract.setNbrMaxRestaurateur(form.getNbrMaxRestaurateur());

        enseigne.setActive(true);
        enseigne.setNom(form.getNom());
        enseigne.setNomContact(form.getNomContact());
        enseigne.setEmailContact(form.getEmail());

        enseigne = enseigne$.save(enseigne);

        if (form.getRestaurateur() != null) {
            restaurateur$.createRestaurateur(enseigne, form.getRestaurateur());
        }

        return enseigne;

    }

    @Override
    public Enseigne activeOrDesactiveEnseigne(Enseigne enseigne, boolean isActive) {

        Assert.isTrue(enseigne.isActive() == isActive, "enseigne [" + enseigne.getId() + "] est deja " + (isActive ? "activer" : "desactiver"));
        enseigne.setActive(isActive);
        enseigne.getContract().setDateDesactivation((isActive ? null : LocalDate.now()));

        if (isActive)
            enseigne.getContract().setDateDerniereActivation(LocalDate.now());


        return enseigne$.save(enseigne);
    }

    @Override
    public Optional<Enseigne> getEnseigneById(Long id) {
        return enseigne$.findById(id);
    }

    @Override
    public Page<Enseigne> getAllEnseignes(int page, int size) {
        return enseigne$.findAll(PageRequest.of(page, size));
    }

    @Override
    public Page<Enseigne> getAllActiveEnseignes(int page, int size) {
        return enseigne$.findAllByActive(true, PageRequest.of(page, size));
    }

    @Override
    public Page<Enseigne> getAllDesativeEnseignes(int page, int size) {
        return enseigne$.findAllByActive(false, PageRequest.of(page, size));
    }
}
