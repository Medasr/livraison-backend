package com.vinci.livraison.app.controllers;


import com.vinci.livraison.app.module.article.entity.Article;
import com.vinci.livraison.app.module.article.service.impl.ArticleService;
import com.vinci.livraison.app.module.restaurateur.entity.Restaurateur;
import com.vinci.livraison.app.module.restaurateur.repository.VilleRepository;
import com.vinci.livraison.app.module.restaurateur.service.impl.RestaurateurService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("api/test")
public class TestController {

    ArticleService article$;

    VilleRepository ville$;

    RestaurateurService restaurateur$;

    @GetMapping("restaurateurs/{restaurateur}")
    public ResponseEntity getRestaurateur(@PathVariable Restaurateur restaurateur){



            restaurateur.getRestaurateurTypes();


            Page<Restaurateur> result = restaurateur$.findRestaurateursByVille(ville$.getOne(1L), PageRequest.of(0,10));

            return ResponseEntity.ok(result);


    }

    @GetMapping("restaurateurs/{restaurateur}/articles")
    public Page<Article> getArticlesByRestaurateur(@PathVariable Restaurateur restaurateur,@PageableDefault() Pageable pageable){

        Assert.notNull(restaurateur,"restaurateur n'existe pas");
        System.out.println(restaurateur);
        return article$.findArticlesByRestaurateur(restaurateur,pageable);
    }

    @GetMapping("restaurateurs/{restaurateur}/articles/{article}")
    public Article getArticleByRestaurateur(@PathVariable Restaurateur restaurateur,@PathVariable Article article,@PageableDefault() Pageable pageable){



        return article;
    }


}
