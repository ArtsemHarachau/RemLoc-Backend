package com.example.postgresql.controllers;

import com.example.postgresql.entities.CityGames;
import com.example.postgresql.entities.GameParticipants;
import com.example.postgresql.services.GameParticipantsService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class GameParticipantsController {

    @Autowired
    private GameParticipantsService gameParticipantsService;


//    @PostMapping("/saveCityGame")
////    @CrossOrigin(origins = "http://localhost:4200")
//    void saveCityGame(@RequestBody CityGames cityGame) {
//        cityGamesService.saveCityGame(cityGame);
//    }

    @PostMapping("/addgameparticipant")
    void saveGameParticipant(@RequestBody GameParticipants gameParticipant){
        gameParticipantsService.addGameParticipant(gameParticipant);
    }


//    /**
//     * Save game participants to database.
//     *
//     */
//    @RequestMapping(value = "/participant", method = RequestMethod.POST)
//    public ResponseEntity<GameParticipants> createGameParticipants(@RequestBody @NotNull GameParticipants gameParticipant) {
//        gameParticipantsService.saveGameParticipant(gameParticipant);
//
//        return ResponseEntity.ok().body(gameParticipant);
//    }
//
//    /**
//     * List all participants.
//     *
//     */
//    @RequestMapping(value = "/participants", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
//    public Iterable<GameParticipants> listParticipants(Model model) {
//        return gameParticipantsService.listAllParticipants();
//    }
}