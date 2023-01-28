package com.example.postgresql.services;

import com.example.postgresql.entities.CityGames;
import com.example.postgresql.repositories.CityGamesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

@Service
public class CityGamesService {

    @Autowired
    private CityGamesRepository cityGamesRepository;

    public void saveCityGame(CityGames citygame){
        cityGamesRepository.save(citygame);
    }



//    @RequestParam("nameOfGame") String nameOfGame,
//    @RequestParam("accessCode") Integer accessCode,
//    @RequestParam("cityForGame") String cityForGame,
//    @RequestParam("dateForStartGame") String dateForStartGame,
//    @RequestParam("countryForGame") String countryForGame
    public Long getCityGameId(String nameOfGame,
                              Integer accessCode,
                              String cityForGame,
                              String dateForStartGame,
                              String countryForGame) {
        return cityGamesRepository.getCityGameIdByAnotherValues(nameOfGame, accessCode, cityForGame, dateForStartGame,
                countryForGame);
    }

//    public CityGames saveCityGame(CityGames cityGame){
//        return cityGamesRepository.save(cityGame);
//    }
//
//    public Iterable<CityGames> listAllGames() {
//        return cityGamesRepository.findAll();
//    }
}

