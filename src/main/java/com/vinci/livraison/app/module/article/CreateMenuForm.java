package com.vinci.livraison.app.module.article;

import lombok.Getter;

import javax.validation.constraints.*;
import java.util.Set;

@Getter
public class CreateMenuForm extends CreateProduitForm {

    @NotEmpty
    Set<Long> produits;

}
