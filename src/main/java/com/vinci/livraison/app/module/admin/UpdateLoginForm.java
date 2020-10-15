package com.vinci.livraison.app.module.admin;

import lombok.Getter;

import javax.validation.constraints.Email;

@Getter
public class UpdateLoginForm {

    @Email(message = "Login non valide")
    String login;
}
