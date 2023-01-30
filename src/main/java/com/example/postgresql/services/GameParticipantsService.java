package com.example.postgresql.services;

import com.example.postgresql.entities.CityGames;
import com.example.postgresql.entities.GameParticipants;
import com.example.postgresql.repositories.GameParticipantsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GameParticipantsService {

    @Autowired
    private GameParticipantsRepository gameParticipantsRepository;

//    public GameParticipants saveGameParticipant(GameParticipants gameParticipant){
//        return gameParticipantsRepository.save(gameParticipant);
//    }
//
//    public Iterable<GameParticipants> listAllParticipants() {
//        return gameParticipantsRepository.findAll();
//    }

    public void addGameParticipant(GameParticipants gameParticipant){
        gameParticipantsRepository.save(gameParticipant);
    }
}
