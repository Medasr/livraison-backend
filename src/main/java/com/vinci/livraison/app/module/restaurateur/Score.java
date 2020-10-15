package com.vinci.livraison.app.module.restaurateur;

import lombok.Value;

import java.util.Optional;


@Value
public class Score {

    Double scoreRestaurateur;
    Double scoreLivreur;

    public Score(Double scoreRestaurateur, Double scoreLivreur) {
        this.scoreRestaurateur = getOrDefault(scoreRestaurateur,0D);
        this.scoreLivreur = getOrDefault(scoreLivreur,0D);
    }

    static <V> V getOrDefault(V value,V _default){
        return value != null ? value :_default;
    }

}
