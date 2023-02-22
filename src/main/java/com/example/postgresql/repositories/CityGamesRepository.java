package com.example.postgresql.repositories;

import com.example.postgresql.entities.CityGames;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Repository
//@CrossOrigin(origins = "http://localhost:4200")
public interface CityGamesRepository extends JpaRepository<CityGames,Long> {

    @Query("SELECT cg.idGame FROM CityGames cg WHERE cg.nameOfGame = :nameOfGame and cg.accessCode = :accessCode and " +
        "cg.cityForGame = :cityForGame and cg.dateForStartGame = :dateForStartGame and " +
        "cg.countryForGame = :countryForGame")
    Long getCityGameIdByAnotherValues(@Param("nameOfGame") String nameOfGame,
                                      @Param("accessCode") Integer accessCode,
                                      @Param("cityForGame") String cityForGame,
                                      @Param("dateForStartGame") String dateForStartGame,
                                      @Param("countryForGame") String countryForGame);


    @Query("FROM CityGames")
    List<CityGames> getCityGamesOnly();



//    @Query("SELECT cg.idGame as idGame, cg.nameOfGame as nameOfGame, cg.accessCode, cg.cityForGame, cg.dateForStartGame, cg.dateForEndGame, cg.countryForGame FROM CityGames cg")
//    List<Object[]> getCityGamesOnly();

}

