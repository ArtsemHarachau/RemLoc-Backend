package com.example.postgresql.controllers;

import com.example.postgresql.entities.Administrators;
import com.example.postgresql.entities.CityGames;
import com.example.postgresql.entities.GameParticipants;
import com.example.postgresql.services.CityGamesService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping("/api")
public class CityGamesController {

    @Autowired
    private CityGamesService cityGamesService;

    @RequestMapping(value = "/index")
    public String index(){
        return "index";
    }


    @GetMapping("/createnewgame")
    public String createNewGame(/*Model model*/) {
//        CityGames cityGame = new CityGames();
//        model.addAttribute("cityGame", cityGame);
        return "newcitygame";//
    }

//    @PostMapping("/saveCityGame")
//    public String saveCityGame(@ModelAttribute("cityGame") CityGames cityGame) {
//        cityGamesService.saveCityGame(cityGame);
//        return "redirect:/";//
//    }


    @PostMapping("/saveCityGame")
//    @CrossOrigin(origins = "http://localhost:4200")
    void saveCityGame(@RequestBody CityGames cityGame) {
        cityGamesService.saveCityGame(cityGame);
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
        Set<GameParticipants> setParticipants = new HashSet<GameParticipants>();
        Administrators part1 = new Administrators();
        part1.nickname = "Lala12";

        Set<CityGames> setGames = new HashSet<CityGames>();
        setGames.add(cityGames);

        part1.setPlayingGames(setGames);

        setParticipants.add(part1);

        cityGames.setPlaying(setParticipants);



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
