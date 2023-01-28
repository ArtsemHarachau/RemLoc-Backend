package com.example.postgresql.services;

import com.example.postgresql.entities.GameParticipants;
import com.example.postgresql.entities.Players;
import com.example.postgresql.repositories.PlayersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayersService {

    @Autowired
    private PlayersRepository playersRepository;

//    public void savePlayer(Players player){
//        playersRepository.insertNickname(player.getNickname());
////        return playersRepository.save(player);
//    }

}
