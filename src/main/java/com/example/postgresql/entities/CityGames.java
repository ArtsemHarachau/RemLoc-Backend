package com.example.postgresql.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "CityGames")
public class CityGames {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "idGame", nullable = false)
    private Long idGame;

    @Column(name = "nameOfGame", nullable = false)
    private String nameOfGame;

    @Column(name = "accessCode", nullable = false)
    private Integer accessCode;

    @Column(name = "cityForGame", nullable = false)
    private String cityForGame;

    @Column(name = "dateForStartGame", nullable = false)
    private String dateForStartGame;

    @Column(name = "dateForEndGame", nullable = true)
    private String dateForEndGame;

    @Column(name = "countryForGame", nullable = false)
    private String countryForGame;


    @ManyToMany(mappedBy = "playingGames", fetch = FetchType.EAGER,cascade = CascadeType.ALL)
//    @JsonIgnore
    @JsonManagedReference
    @JsonIgnore
    private Set<GameParticipants> playing = new HashSet<>();


    @OneToMany(mappedBy = "cityGame", fetch = FetchType.EAGER)
    @JsonManagedReference
//    @JsonIgnore
    private Set<PlacesOfGame> placesOfGame = new HashSet<>();

    public Long getIdGame() {
        return idGame;
    }

    public void setIdGame(Long idGame) {
        this.idGame = idGame;
    }

    public String getNameOfGame() {
        return nameOfGame;
    }

    public void setNameOfGame(String nameOfGame) {
        this.nameOfGame = nameOfGame;
    }

    public Integer getAccessCode() {
        return accessCode;
    }

    public void setAccessCode(Integer accessCode) {
        this.accessCode = accessCode;
    }

    public String getCityForGame() {
        return cityForGame;
    }

    public void setCityForGame(String cityForGame) {
        this.cityForGame = cityForGame;
    }

    public String getDateForStartGame() {
        return dateForStartGame;
    }

    public void setDateForStartGame(String dateForStartGame) {
        this.dateForStartGame = dateForStartGame;
    }

    public String getDateForEndGame() {
        return dateForEndGame;
    }

    public void setDateForEndGame(String dateForEndGame) {
        this.dateForEndGame = dateForEndGame;
    }

    public String getCountryForGame() {
        return countryForGame;
    }

    public void setCountryForGame(String countryForGame) {
        this.countryForGame = countryForGame;
    }

    public Set<GameParticipants> getPlaying() {
        return playing;
    }

    public void setPlaying(Set<GameParticipants> playing) {
        this.playing = playing;
    }

    public Set<PlacesOfGame> getPlacesOfGame() {
        return placesOfGame;
    }

    public void setPlacesOfGame(Set<PlacesOfGame> placesOfGame) {
        this.placesOfGame = placesOfGame;
    }


}
