package com.example.postgresql.controllers;

import com.example.postgresql.entities.Administrators;
import com.example.postgresql.entities.CityGames;
import com.example.postgresql.entities.GameParticipants;
import com.example.postgresql.entities.PlacesOfGame;
import com.example.postgresql.services.CityGamesService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class CityGamesController {

    @Autowired
    private CityGamesService cityGamesService;

//    @PostMapping("/saveCityGame")
//    public String saveCityGame(@ModelAttribute("cityGame") CityGames cityGame) {
//        cityGamesService.saveCityGame(cityGame);
//        return "redirect:/";//
//    }


    /**
     *
     * @param cityGame
     * save cityGame into database
     */
    @PostMapping("/savegame")
//    @CrossOrigin(origins = "http://localhost:4200")
    void saveCityGame(@RequestBody CityGames cityGame) {

        cityGamesService.saveCityGame(cityGame);
    }

    /**
     *
     * @param cityGame
     * add new player to cityGame
     */
    @PostMapping("/addplayer")
    void addPlayerToGame(@RequestBody CityGames cityGame){

    }

    /**
     *
     * @param cityGame
     * add admin to cityGame
     */
    @PostMapping("/addadmin")
    void addAdminToGame(@RequestBody CityGames cityGame){

    }


    /**
     *
     * @param id
     * find cityGame by id
     * @return cityGame from database
     */
    @GetMapping("/citygame/{id}")
    public ResponseEntity<CityGames> findGameById(@PathVariable Long id){
        Optional<CityGames> cityGame = cityGamesService.findGameById(id);

        return cityGame.map(cityGames -> new ResponseEntity<>(cityGames, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(cityGame.get(), HttpStatus.NOT_FOUND));
    }

    @PostMapping("/addplacetogame")
    public void addNewPlaceToGame(@RequestBody CityGames cityGame){

        Set<PlacesOfGame> setPlacesOfGame = new HashSet<>();
        PlacesOfGame placeOfGame = new PlacesOfGame();
        placeOfGame.setOrderId(1);
        placeOfGame.setAddress("Serbska 17");
        placeOfGame.setLegend("Lalalalalaa");
        placeOfGame.setPhotoLink("Blablablabla");
        placeOfGame.setLatitudeCoord(1.1);
        placeOfGame.setLongitudeCoord(1.1);

        setPlacesOfGame.add(placeOfGame);

        cityGame.setPlacesOfGame(setPlacesOfGame);

        cityGamesService.saveCityGame(cityGame);
    }

    @PostMapping("/updatecitygame/{id}")
    public ResponseEntity<CityGames> updateGame(@RequestBody CityGames cityGame, @PathVariable Long id){
//        CityGames copiedCityGame = cityGamesService.findGameById(id);

        Optional<CityGames> cityGameForUpdate = cityGamesService.findGameById(id);
        return cityGameForUpdate
                .map(updatedCityGame -> {
                    updatedCityGame.setNameOfGame(cityGame.getNameOfGame());
                    updatedCityGame.setCityForGame(cityGame.getCityForGame());
                    updatedCityGame.setNameOfGame(cityGame.getNameOfGame());
                    updatedCityGame.setAccessCode(cityGame.getAccessCode());
                    updatedCityGame.setCountryForGame(cityGame.getCountryForGame());
                    updatedCityGame.setDateForStartGame(cityGame.getDateForStartGame());
                    updatedCityGame.setDateForEndGame(cityGame.getDateForEndGame());
                    updatedCityGame.setPlacesOfGame(cityGame.getPlacesOfGame());
                    updatedCityGame.setPlaying(cityGame.getPlaying());
                    cityGamesService.saveCityGame(updatedCityGame);
                    return new ResponseEntity<>(updatedCityGame, HttpStatus.OK);
                })
                .orElseGet(() -> {
                    cityGamesService.saveCityGame(cityGame);
                    return new ResponseEntity<>(cityGame, HttpStatus.NOT_FOUND);
                });

    }


    /**
     *
     * @param id
     * delete citGame by id
     */
    @DeleteMapping("/citygame/{id}")
    public void deleteGameById(@PathVariable Long id){
        cityGamesService.deleteGameById(id);
    }




//    @GetMapping("users/{id}")
//    public ResponseEntity<User> getById(@PathVariable long id) {
//
//        Optional<User> user = userService.getById(id);
//        if (user.isPresent()) {
//            return new ResponseEntity<>(user.get(), HttpStatus.OK);
//        } else {
//            throw new RecordNotFoundException();
//        }
//    }

//    @Param("nameOfGame") String nameOfGame,
//    @Param("accessCode") Integer accessCode,
//    @Param("cityForGame") String cityForGame,
//    @Param("dateForStartGame") String dateForStartGame,
//    @Param("countryForGame") String countryForGame


//    @GetMapping("/citygame")
//    @ResponseBody
//    public ResponseEntity<Long> getCityGameId(@RequestParam("nameOfGame") String nameOfGame,
//                                              @RequestParam("accessCode") Integer accessCode,
//                                              @RequestParam("cityForGame") String cityForGame,
//                                              @RequestParam("dateForStartGame") String dateForStartGame,
//                                              @RequestParam("countryForGame") String countryForGame){
//        Long idGame = cityGamesService.getCityGameId(nameOfGame, accessCode, cityForGame, dateForStartGame,
//                countryForGame);
//
//        System.out.println("IDGAME: -> " + idGame);
//
//        return new ResponseEntity<>(idGame, HttpStatus.OK);
//    }

    @PostMapping("/citygame")
//    @ResponseBody
    public ResponseEntity<Long> getCityGameId(@RequestBody CityGames cityGames){
//        Long testIdGame = cityGamesService.getCityGameId(cityGames.getNameOfGame(), cityGames.getAccessCode(), cityGames.getCityForGame(), cityGames.getDateForStartGame(),
//                cityGames.getCountryForGame());

        Long testIdGame = cityGamesService.getCityGameId(cityGames.getNameOfGame(), cityGames.getAccessCode(),
                cityGames.getCityForGame(), cityGames.getDateForStartGame(), cityGames.getCountryForGame());

        System.out.println("IDGAME: -> " + testIdGame);
        Set<GameParticipants> listParticipants = new HashSet<>();
        Administrators part1 = new Administrators();
        part1.nickname = "Lala12";

        Set<CityGames> listGames = new HashSet<>();
        listGames.add(cityGames);

        part1.setPlayingGames(listGames);

        listParticipants.add(part1);

        cityGames.setPlaying(listParticipants);



        cityGamesService.saveCityGame(cityGames);

        return new ResponseEntity<>(testIdGame, HttpStatus.OK);
    }


//    @PostMapping("/createNewGame")
//    public String createNewCityGame(Model model){
//        CityGames cityGame = new CityGames();
//        model.addAttribute("cityGame", cityGame);
//
//    }


//    /**
//     * Save city game to database.
//     *
//     */
//    @RequestMapping(value = "/city/game", method = RequestMethod.POST)
//    public ResponseEntity<CityGames> createCityGame(@RequestBody @NotNull CityGames cityGame) {
//        cityGamesService.saveCityGame(cityGame);
//        return ResponseEntity.ok().body(cityGame);
//    }
//
//    /**
//     * List all games.
//     *
//     */
//    @RequestMapping(value = "/city/games", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    public Iterable<CityGames> listGames(Model model) {
//        return cityGamesService.listAllGames();
//    }
}
