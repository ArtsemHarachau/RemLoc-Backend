package com.example.postgresql.services;


import com.example.postgresql.repositories.GameParticipantsRepository;
import com.example.postgresql.repositories.PlacesOfGameRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlacesOfGameService {

    @Autowired
    private PlacesOfGameRepository placesOfGameRepository;
}
