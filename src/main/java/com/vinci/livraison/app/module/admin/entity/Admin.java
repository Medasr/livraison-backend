package com.vinci.livraison.app.module.admin.entity;

import com.vinci.livraison.app.module.shared.User;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.Entity;

@Entity
@Getter @Setter
@Accessors(chain = true)
public class Admin extends User {

    public Admin() {
        super();
    }

    public Admin(String login, String password) {
        super(login, password);
    }
}
