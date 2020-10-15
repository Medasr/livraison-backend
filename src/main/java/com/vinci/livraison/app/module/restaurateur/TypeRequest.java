package com.vinci.livraison.app.module.restaurateur;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class TypeRequest {

    @NotBlank(message = "nom est requis")
    String titre;

}