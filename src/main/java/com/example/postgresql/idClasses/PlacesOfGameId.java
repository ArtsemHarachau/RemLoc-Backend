package com.example.postgresql.idClasses;

import com.example.postgresql.entities.CityGames;

import java.io.Serializable;

public class PlacesOfGameId implements Serializable {

    private Integer orderId;
    private CityGames cityGame;

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}
