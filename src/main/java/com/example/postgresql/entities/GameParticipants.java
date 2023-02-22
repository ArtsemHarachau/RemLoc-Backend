package com.example.postgresql.entities;

import com.fasterxml.jackson.annotation.*;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.List;
import java.util.Set;
import java.util.HashSet;


@Entity
//@Table(name = "GameParticipants")
@Inheritance(strategy = InheritanceType.JOINED)
//@JsonTypeInfo( use = JsonTypeInfo.Id.CLASS, include = JsonTypeInfo.As.PROPERTY)
//@JsonSubTypes({
//        @JsonSubTypes.Type(value = Administrators.class, name = "admins"),
//        @JsonSubTypes.Type(value = Players.class, name = "players")
//})
public abstract class GameParticipants {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "idParticipant", nullable = false)
    public Long idParticipant;

    @Column(name = "nickname", nullable = false)
    public String nickname;

    @ManyToMany()
    @JoinTable(name = "playingGames",
            joinColumns = @JoinColumn(name = "idParticipant"),
            inverseJoinColumns = @JoinColumn(name = "idGame"))
    @JsonBackReference
//    @JsonIgnore
    public Set<CityGames> playingGames = new HashSet<>();


    public Long getIdParticipant() {
        return idParticipant;
    }

    public void setIdParticipant(Long idParticipant) {
        this.idParticipant = idParticipant;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public Set<CityGames> getPlayingGames() {
        return playingGames;
    }

    public void setPlayingGames(Set<CityGames> playingGames) {
        this.playingGames = playingGames;
    }


}
