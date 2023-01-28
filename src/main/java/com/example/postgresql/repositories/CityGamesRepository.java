package com.example.postgresql.repositories;

import com.example.postgresql.entities.CityGames;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestParam;

@Repository
//@CrossOrigin(origins = "http://localhost:4200")
public interface CityGamesRepository extends JpaRepository<CityGames,Long> {


//    @Query("SELECT u FROM User u WHERE u.status = :status and u.name = :name")
//    User findUserByUserStatusAndUserName(@Param("status") Integer userStatus,
//                                         @Param("name") String userName);

//    @Query("SELECT cg.idGame FROM CityGames cg WHERE cg.nameOfGame = :nameOfGame and cg.accessCode = :accessCode and " +
//            "cg.cityForGame = :cityForGame and cg.dateForStartGame = :dateForStartGame and " +
//            "cg.countryForGame = :countryForGame")
//    @Query("SELECT cg.idGame FROM CityGames cg WHERE cg.nameOfGame = 'Hobbit' and cg.accessCode = 12345 and " +
//            "cg.cityForGame = 'Poznan' and cg.dateForStartGame = '2023-01-16' and " +
//            "cg.countryForGame = 'Poland'")
    @Query("SELECT cg.idGame FROM CityGames cg WHERE cg.nameOfGame = :nameOfGame and cg.accessCode = :accessCode and " +
        "cg.cityForGame = :cityForGame and cg.dateForStartGame = :dateForStartGame and " +
        "cg.countryForGame = :countryForGame")
    Long getCityGameIdByAnotherValues(@Param("nameOfGame") String nameOfGame,
                                      @Param("accessCode") Integer accessCode,
                                      @Param("cityForGame") String cityForGame,
                                      @Param("dateForStartGame") String dateForStartGame,
                                      @Param("countryForGame") String countryForGame);


//    @Query("SELECT cg.idGame FROM CityGames cg WHERE cg.nameOfGame = :nameOfGame and cg.accessCode = :accessCode and " +
//            "cg.cityForGame = :cityForGame and cg.dateForStartGame = :dateForStartGame and " +
//            "cg.countryForGame = :countryForGame")
//    Long getCityGameIdByAnotherValues();
//    Long getCityGameIdByAnotherValues(@RequestParam("nameOfGame") String nameOfGame,
//                                      @RequestParam("accessCode") Integer accessCode,
//                                      @RequestParam("cityForGame") String cityForGame,
//                                      @RequestParam("dateForStartGame") String dateForStartGame,
//                                      @RequestParam("countryForGame") String countryForGame);
}

