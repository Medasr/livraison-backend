package com.vinci.livraison.app.controllers;


import com.vinci.livraison.app.module.admin.entity.Admin;
import com.vinci.livraison.app.module.admin.repository.AdminRepository;
import com.vinci.livraison.app.module.admin.service.AdminService;
import com.vinci.livraison.app.module.article.entity.Article;
import com.vinci.livraison.app.module.article.entity.Categorie;
import com.vinci.livraison.app.module.article.repository.ArticleRepository;
import com.vinci.livraison.app.module.client.CreateClientForm;
import com.vinci.livraison.app.module.client.entity.Client;
import com.vinci.livraison.app.module.client.repository.ClientRepository;
import com.vinci.livraison.app.module.restaurateur.entity.Restaurateur;
import com.vinci.livraison.app.module.restaurateur.repository.RestaurateurRepository;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
@AllArgsConstructor
public class TestController {

    @Autowired
    final ClientRepository clientRepository;

    @Autowired
    final RestaurateurRepository restaurateurRepository;

    @Autowired
    final PasswordEncoder encoder;

    @Autowired
    AdminService adminService;

    @Autowired
    AdminRepository adminRepository;

    @Autowired
    ArticleRepository articleRepository;


    private void init() {

        if (adminRepository.count() > 0) return;
        adminRepository.saveAll(Arrays.asList(
                new Admin("admin@dev.info", "$2a$10$aIzzdWbsv9pAVsqCKRqITekOb/z.mK4kbbNrBNwWw1pmMQV.XEC1e"),
                new Admin("admin2@dev.info", "$2a$10$aIzzdWbsv9pAVsqCKRqITekOb/z.mK4kbbNrBNwWw1pmMQV.XEC1e"),
                new Admin("admin3@dev.info", "$2a$10$aIzzdWbsv9pAVsqCKRqITekOb/z.mK4kbbNrBNwWw1pmMQV.XEC1e"),
                new Admin("admin4@dev.info", "$2a$10$aIzzdWbsv9pAVsqCKRqITekOb/z.mK4kbbNrBNwWw1pmMQV.XEC1e"),
                new Admin("admin5@dev.info", "$2a$10$aIzzdWbsv9pAVsqCKRqITekOb/z.mK4kbbNrBNwWw1pmMQV.XEC1e"),
                new Admin("admin6@dev.info", "$2a$10$aIzzdWbsv9pAVsqCKRqITekOb/z.mK4kbbNrBNwWw1pmMQV.XEC1e"),
                new Admin("admin7@dev.info", "$2a$10$aIzzdWbsv9pAVsqCKRqITekOb/z.mK4kbbNrBNwWw1pmMQV.XEC1e"),
                new Admin("admin8@dev.info", "$2a$10$aIzzdWbsv9pAVsqCKRqITekOb/z.mK4kbbNrBNwWw1pmMQV.XEC1e")
        ));
    }

    @GetMapping("testqueryparam")
    public ResponseEntity testQueryParam(@RequestBody(required = false) MyQueryParams paramsbody, MyQueryParams params, @RequestParam String prop2) {

        return ResponseEntity.ok(Arrays.asList(paramsbody, params, prop2));

    }

    @GetMapping(value = "testqueryparam", params = {"prop2"})
    public ResponseEntity testQuery$Param(@RequestBody(required = false) MyQueryParams paramsbody, @RequestParam String prop2) {

        return ResponseEntity.ok(Arrays.asList(paramsbody, prop2));

    }

    @GetMapping("testarray")
    public ResponseEntity testArray(@RequestBody List<TestForm> testForms) {

        return ResponseEntity.ok(testForms);

    }

    @Getter
    public static class TestForm {

        String prop;

    }

    @GetMapping("test")
    public ResponseEntity test() {

        Categorie categorie = new Categorie();
        categorie.setId(1L);

        Restaurateur restaurateur = restaurateurRepository.findById(1L).map(restaurateur$ -> {

            restaurateur$.getVille();

            restaurateurRepository.getRestaurateurScore(restaurateur$).ifPresent(score -> {
                restaurateur$.setScoreRestaurateur(score.getScoreRestaurateur());
                restaurateur$.setScoreLivreur(score.getScoreLivreur());
            });

            return restaurateur$;

        }).orElse(null);

        return ResponseEntity.ok(restaurateur);

    }

    @PostMapping("create-client")
    public ResponseEntity home(@RequestBody(required = false) CreateClientForm clientForm, @RequestParam(required = false) List<Long> props) {


        Categorie categorie = new Categorie();
        categorie.setId(1L);
        Page<Article> articlesByCategorie = articleRepository.findArticlesByCategorie(categorie, PageRequest.of(0, 5));


        System.out.println(props);

        Client client = clientRepository.findById(2L).map(client1 -> {
            System.out.println("adressse ------");
            client1.setNom("Med Oussafi");
            client1.setTel("0612345678");
            client1.setLogin("med12@dev.com");
            client1.setPassword(encoder.encode("password"));
            return client1;
        }).get();
        System.out.println("adressse ------");


        client = clientRepository.save(client);
        client.setNom("Med Moussa");

        System.out.println("adressse ------");
        return ResponseEntity.ok(Arrays.asList(clientForm, client, articlesByCategorie));

    }

    @GetMapping("get/{id}")
    public ResponseEntity home1(@PathVariable Long id) {

        Restaurateur restaurateur = new Restaurateur();
        restaurateur.setId(id);
        restaurateurRepository.getRestaurateurScore(restaurateur);

        return ResponseEntity.ok().build();

    }

    @DeleteMapping("delete/{id}")
    @Transactional
    public ResponseEntity home3(@PathVariable Long id) {

        return clientRepository.findById(id).map(client -> {
            clientRepository.delete(client);
            return ResponseEntity.noContent().build();
        })
                .orElse(ResponseEntity.badRequest().build());

    }
}
