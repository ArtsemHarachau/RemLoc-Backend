package com.example.postgresql.entities;

import com.example.postgresql.idClasses.PlacesOfGameId;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "PlacesOfGame")
@IdClass(PlacesOfGameId.class)
public class PlacesOfGame implements Serializable {

    @Id
    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "historyOfPlace", nullable = false)
    private String historyOfPlace;

    @Column(name = "taskInPlace", nullable = false)
    private String taskInPlace;

    @Column(name = "correctAnswer")
    private String correctAnswer;


    @Id
    @ManyToOne
    @JoinColumn(name = "idGame", nullable = false)
    private CityGames cityGame;


}
