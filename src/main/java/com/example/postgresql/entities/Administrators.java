package com.example.postgresql.entities;

import javax.persistence.Entity;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
//@Table(name = "Administrators")
@PrimaryKeyJoinColumn(name = "adminId")
public class Administrators extends GameParticipants{

    public Administrators(){
        super();
    }
}
