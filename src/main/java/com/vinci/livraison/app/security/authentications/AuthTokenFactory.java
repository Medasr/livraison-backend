package com.vinci.livraison.app.security.authentications;

import com.vinci.livraison.app.security.authentications.impl.*;
import org.springframework.security.core.Authentication;

public class AuthTokenFactory {

    public static Authentication getAuthentication(UserType type, Long id) {
        switch (type) {
            case CLIENT:
                return new ClientAuthToken(id);
            case RESTAURATEUR:
                return new RestaurateurAuthToken(id);
            case ADMIN:
                return new AdminAuthToken(id);
            case LIVREUR:
                return new LivreurAuthToken(id);
            case ENSEIGNE:
                return new EnseigneAuthToken(id);
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }

    public static Authentication getAuthentication(UserType type, String login, String pasword) throws IllegalStateException{
        switch (type) {
            case CLIENT:
                return new ClientAuthToken(login, pasword);
            case RESTAURATEUR:
                return new RestaurateurAuthToken(login, pasword);
            case ADMIN:
                return new AdminAuthToken(login, pasword);
            case LIVREUR:
                return new LivreurAuthToken(login, pasword);
            default:
                throw new IllegalStateException("Unexpected value: " + type);
        }
    }
}
