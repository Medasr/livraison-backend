package com.vinci.livraison.app.module.enseigne.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Getter
@Setter
public class Enseigne implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nom;
    @Column(nullable = false)
    private String nomContact;


    @Column(nullable = false, unique = true)
    private String emailContact;


    @OneToOne(mappedBy = "enseigne", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Contract contract;

    @Column(name = "is_active")
    private boolean active = true;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass() || id == null) return false;

        Enseigne obj = (Enseigne) o;

        return id.equals(obj.id);
    }

    @Override
    public int hashCode() {
        if (id == null) return 0;
        return id.hashCode();
    }


}
