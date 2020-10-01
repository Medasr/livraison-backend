package com.vinci.livraison.app.module.client.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@NoArgsConstructor
@Getter
@Setter

public class AdresseLivraison implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_client")
    private Client client;

    // isPrimary adresse
    @Column(name = "is_primary")
    private boolean primary = false;

    // adresse
    @Column(nullable = false)
    private String adresse;

    // geolocation
    @Embedded
    private GeoLocation geoLocation;

    @Embeddable
    @Getter
    @Setter
    public static class GeoLocation implements Serializable {
        @Column(name = "latitude")
        private Double latitude;

        @Column(name = "longitude")
        private Double longitude;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass() || id == null) return false;

        AdresseLivraison obj = (AdresseLivraison) o;

        return id.equals(obj.id);
    }

    @Override
    public int hashCode() {
        if (id == null) return 0;
        return id.hashCode();
    }

}
