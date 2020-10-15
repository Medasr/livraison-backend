package com.vinci.livraison.app.controllers.auth;

import com.vinci.livraison.app.security.authentications.UserType;
import lombok.Getter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class LoginRequest {

    @NotNull(message = "User type est requis")
    UserType userType;

    @NotBlank(message = "login est requis")
    @Email(message = "login doit être une adresse e-mail valide")
    String login;
    String password;
}
