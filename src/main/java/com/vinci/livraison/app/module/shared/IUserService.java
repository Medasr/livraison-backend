package com.vinci.livraison.app.module.shared;

import java.util.Optional;

public interface IUserService<T> {

    Optional<T> findByLogin(String login);

}
