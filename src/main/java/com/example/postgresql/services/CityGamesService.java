package com.example.postgresql.services;

import com.example.postgresql.entities.CityGames;
import com.example.postgresql.repositories.CityGamesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CityGamesService {

    @Autowired
    private CityGamesRepository cityGamesRepository;


    public void saveCityGame(CityGames citygame){
        cityGamesRepository.save(citygame);
    }


    public Long getCityGameId(String nameOfGame,
                              Integer accessCode,
                              String cityForGame,
                              String dateForStartGame,
                              String countryForGame) {
        return cityGamesRepository.getCityGameIdByAnotherValues(nameOfGame, accessCode, cityForGame, dateForStartGame,
                countryForGame);
    }


    public CityGames findGameById(Long idGame){
        return cityGamesRepository.findById(idGame).orElse(null);
    }

    public void deleteGameById(Long idGame){
        cityGamesRepository.deleteById(idGame);
    }

    public List<CityGames> getAllCityGames(){
        return cityGamesRepository.findAll();
    }

    public List<CityGames> getCityGamesOnly(){
        return cityGamesRepository.getCityGamesOnly();
    }
}

