package com.vinci.livraison.app.module.shared;


import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;


@MappedSuperclass
@NoArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
public abstract class User implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @Column(name = "login", nullable = false, unique = true)
    protected String login;

    @JsonIgnore
    @Column(name = "password", nullable = false)
    protected String password;

    @Column(name = "date_creation",nullable = false,updatable = false)
    protected LocalDateTime dateCreation;

    public User(String login, String password) {
        this.login = login;
        this.password = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if ( o == null || getClass() != o.getClass() || id == null ) return false;

        User obj = (User) o;

        return id.equals(obj.id);
    }

    @Override
    public int hashCode() {
        if (id == null) return 0;
        return id.hashCode();
    }
}
