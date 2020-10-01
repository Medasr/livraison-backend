package com.vinci.livraison.app.module.restaurateur.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "resteurateur_type")
@Data
@NoArgsConstructor
public class RestaurateurType implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_type")
    private Type type;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_restaurateur")
    private Restaurateur restaurateur;


    public RestaurateurType(Type type, Restaurateur restaurateur) {
        this.type = type;
        this.restaurateur = restaurateur;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if ( o == null || getClass() != o.getClass() || id == null ) return false;

        RestaurateurType obj = (RestaurateurType) o;

        return id.equals(obj.id);
    }

    @Override
    public int hashCode() {
        if (id == null) return 0;
        return id.hashCode();
    }

}
