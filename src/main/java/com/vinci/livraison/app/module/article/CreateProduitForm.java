package com.vinci.livraison.app.module.article;

import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.util.Set;

@Getter
public class CreateProduitForm {

    @NotBlank
    String titre;
    @Positive
    Double prix;

    @NotEmpty
    Set<Long> categories;

}
