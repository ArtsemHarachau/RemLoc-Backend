package com.example.postgresql.entities;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
//@Table(name = "Players")
@PrimaryKeyJoinColumn(name = "playerId")
public class Players extends GameParticipants{

}
