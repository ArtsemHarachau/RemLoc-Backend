package com.example.postgresql.controllers;

import com.example.postgresql.services.AdministratorsService;
import com.example.postgresql.services.PlacesOfGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api")
public class PlacesOfGameController {

    @Autowired
    private PlacesOfGameService placesOfGameService;
}
