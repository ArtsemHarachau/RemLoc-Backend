package com.example.postgresql.controllers;

import com.example.postgresql.entities.GameParticipants;
import com.example.postgresql.entities.Players;
import com.example.postgresql.services.PlayersService;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class PlayersController {

    @Autowired
    private PlayersService playersService;

    /**
     * Save player to database.
     *
     */
//    @RequestMapping(value = "/player", method = RequestMethod.POST)
//    @PostMapping("/player")
//    public void createPlayer(Model model, @RequestParam String name) {
//        playersService.savePlayer(player);
//    }
}
