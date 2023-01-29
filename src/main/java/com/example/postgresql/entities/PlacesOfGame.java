package com.example.postgresql.entities;

import com.example.postgresql.idClasses.PlacesOfGameId;
import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "PlacesOfGame")
@IdClass(PlacesOfGameId.class)
public class PlacesOfGame implements Serializable {

    @Id
    @Column(name = "orderId", nullable = false)
    private Integer orderId;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "latitudeCoord", nullable = false)
    private double latitudeCoord;

    @Column(name = "longitudeCoord", nullable = false)
    private double longitudeCoord;

    @Column(name = "legend", nullable = false)
    private String legend;

    @Column(name = "photoLink", nullable = false)
    private String photoLink;


    @Id
    @ManyToOne
    @JoinColumn(name = "idGame", nullable = false)
//    @JsonBackReference
    private CityGames cityGame;


    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getLatitudeCoord() {
        return latitudeCoord;
    }

    public void setLatitudeCoord(double latitudeCoord) {
        this.latitudeCoord = latitudeCoord;
    }

    public double getLongitudeCoord() {
        return longitudeCoord;
    }

    public void setLongitudeCoord(double longitudeCoord) {
        this.longitudeCoord = longitudeCoord;
    }

    public String getLegend() {
        return legend;
    }

    public void setLegend(String legend) {
        this.legend = legend;
    }

    public String getPhotoLink() {
        return photoLink;
    }

    public void setPhotoLink(String photoLink) {
        this.photoLink = photoLink;
    }

    public CityGames getCityGame() {
        return cityGame;
    }

    public void setCityGame(CityGames cityGame) {
        this.cityGame = cityGame;
    }
}
